package com.potatorice.boot.basic.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
//@runwith(springrunner.class) junit4的写法
@ExtendWith(SpringExtension.class)
@SpringBootTest
class FamilyTest {
    @Resource
    private Family family;

    @Resource
    private Father father;

    @Resource
    private Mother mother;

    @Resource
    private Child child;

    @Resource
    private Friend friend;


    @Test
    void getFamilyName() {
        String familyName = family.getFamilyName();
        log.info("family-name:" + familyName);
        assertEquals("happy family", familyName);
    }

    @Test
    void getFather() {
        String fatherName = father.getName();
        int fatherAge = father.getAge();

        log.info("father-name:" + fatherName);
        log.info("father-age:" + String.valueOf(fatherAge));
        assertEquals("jackson", fatherName);
//        assertEquals(35, fatherAge);
    }

    @Test
    void getMother() {
        String name = mother.getName();
        List<String> alias = mother.getAlias();
//        String alias1 = alias[0];
//        String alias2 = alias[1];
//        String aliasAll = alias1 + alias2;
        log.info("mother-name:" + name);
        log.info("mother-alias:" + String.valueOf(alias));
        assertEquals("MyLittlePony", name);
        assertEquals("[lovely, alice]", String.valueOf(alias));

//        assertEquals("alice", alias[1]);

    }

    @Test
    void getChild() {
        String name = child.getName();
        int age = child.getAge();
        List<Map<String, Object>> friends = child.getFriends();
        log.info("child-name:" + name);
        log.info("child-age:" + String.valueOf(age));
        log.info("child-friend:" + String.valueOf(friends));
//        log.info(String.valueOf(friends[1]));
        assertEquals("jack", name);
//        assertEquals(5, age);
        assertEquals("[{hobby=football, gender=male}, {hobby=sing, gender=female}]", String.valueOf(friends));
//        assertEquals("Friend(hobby=sing, gender=female)", String.valueOf(friends[1]));
    }
}