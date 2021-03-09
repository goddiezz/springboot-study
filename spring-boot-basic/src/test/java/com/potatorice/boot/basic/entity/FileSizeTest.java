package com.potatorice.boot.basic.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
//@runwith(springrunner.class) junit4的写法
@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileSizeTest {
    @Resource
    private FileSize fileSize;

    @Test
    void getMaxSize() {
//        log.info("fileSize" + fileSize.getMaxSize());
        String maxSize = fileSize.getMaxSize();
        log.info(maxSize);
        assertEquals("300MB",maxSize);
    }
}