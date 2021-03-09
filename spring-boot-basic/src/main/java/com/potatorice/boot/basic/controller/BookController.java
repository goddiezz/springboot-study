package com.potatorice.boot.basic.controller;

import com.potatorice.boot.basic.controller.dto.AjaxResponse;
import com.potatorice.boot.basic.entity.Book;
import com.potatorice.boot.basic.entity.BookReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author PotatoRice
 * @description：
 * @date 2021/3/5 12:12 下午
 */
@RestController
@RequestMapping(value = "/api/v1/books")
@Slf4j
@Api(tags = "⽂章管理接⼝")
public class BookController {
    /**
     * 查询⽂章，id为URL查询参数
     *
     * @param id ⽂章id
     * @return AjaxResponse
     */
    @ApiOperation("获取所有图书")
    @GetMapping("all")
    public AjaxResponse selectBooks() {
        BookReader[] readers = {
                BookReader.builder()
                        .name("aaa")
                        .age(20)
                        .build(),
                BookReader.builder()
                        .name("bbb")
                        .age(19)
                        .build(),
        };
        List<BookReader> readerList = Arrays.asList(readers);
        Book book1 = Book.builder()
                .id(123)
                .author("potota")
                .title("SpringBoot")
                .content("从入门到精通")
                .createTime(new Date())
                .readers(readerList)
                .build();
        Book book2 = Book.builder()
                .id(456)
                .author("potota")
                .title("vue.js")
                .content("从入门到精通")
                .createTime(new Date())
                .readers(readerList)
                .build();
        Book[] books = {book1, book2};
        List<Book> bookList = Arrays.asList(books);

        return AjaxResponse.success(bookList);
    }

    @ApiOperation("URL传参，根据id获取⽂章")
    @GetMapping("{id}")
    public AjaxResponse getBook(@PathVariable int id) {
        Book book = Book.builder()
                .id(id)
                .author("potota")
                .title("java")
                .content("java")
                .createTime(new Date())
                .build();
        return AjaxResponse.success(book);
    }

    @GetMapping()
    @ApiOperation("根据id，url传参查询文章")
    public AjaxResponse selectBook(
            @ApiParam("文章id")
            @RequestParam int id
    ){
        Book book = Book.builder()
                .id(id)
                .author("potato")
                .title("《spring》")
                .content("spring学着试试看")
                .createTime(new Date())
                .build();
        return AjaxResponse.success(book);
    }

    @ApiOperation("JSON对象添加图书")
    @PostMapping()
    public AjaxResponse saveBook(@RequestBody Book book) {
        log.info("saveBook" + book);
        return AjaxResponse.success(book);
    }

    @PostMapping("param")
    @ApiOperation("URL传参新增文章")
    public AjaxResponse saveArticle(
            @ApiParam("文章id")
            @RequestParam(value = "id", defaultValue = "111", required = false) int id,
            @ApiParam("作者")
            @RequestParam(value = "author", defaultValue = "potatoRice", required = false) String author,
            @ApiParam("标题")
            @RequestParam String title,
            @ApiParam("内容")
            @RequestParam String content,
            @ApiParam("创建时间")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "createTime", defaultValue = "2021-03-07 23:20:00") Date createTime
    ){
        Book book = Book.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .createTime(createTime)
                .build();
        log.info("saveArticle: " + book);
        return AjaxResponse.success(book);
    }

    @ApiOperation("修改图书信息")
    @PutMapping()
    public AjaxResponse updateBook(@RequestBody Book book) {
        Book book1 = Book.builder()
                .id(111)
                .author("potota")
                .title("java")
                .content("java")
                .createTime(new Date())
                .build();
        log.info("book:" + book1);

        book1.setId(book.getId());
        book1.setAuthor(book.getAuthor());
        book1.setTitle(book.getTitle());
        book1.setContent(book.getContent());

        log.info("book:" + book1);
        return AjaxResponse.success(book1);
    }

    @ApiOperation("删除图书")
    @DeleteMapping("{id}")
    public AjaxResponse deleteArticle(@PathVariable int id) {
        log.info("id:" + id);
        return AjaxResponse.success();
    }

    @ApiOperation("多文件上传,时间分类")
    @PostMapping("upload")
    public AjaxResponse handleUpload(MultipartFile file, HttpServletRequest request) {
        //创建文件在服务器的存放路径
        String path = request.getServletContext().getRealPath("/upload");
        log.info(path);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            boolean flag = fileDir.mkdirs();
            log.info("flag:" + flag);
        }
        //生成文件在服务器的名字的前缀
        String prefixName = UUID.randomUUID().toString();
        //要取得源文件名
        String originalFilename = file.getOriginalFilename();
        //从源文件名中分离出扩展名（后缀） 111.jpg -> .jpg
        assert originalFilename != null;
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //拼接新的文件名
        String fileName = prefixName + suffixName;
        log.info(fileName);
        //创建上传的文件对象
        File saveFile = new File(path + "/" + fileName);
        //上传
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            log.info(e.getMessage());
            AjaxResponse.failure("文件上传失败");
        }
        return AjaxResponse.success(fileName);

    }
}
