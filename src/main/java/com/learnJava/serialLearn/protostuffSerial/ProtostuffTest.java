package com.learnJava.serialLearn.protostuffSerial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import com.learnJava.serialLearn.Person;
import org.apache.commons.lang.StringUtils;

public class ProtostuffTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Person person = new Person();
        person.setName("lsm");
        person.setAge(28);
        person.setSex("男");

        byte[] bytes = ProtostuffUtil.serializer(person);

        Person persionDeserializer = ProtostuffUtil.deserializer(bytes, Person.class);

        System.out.println("bytes:"+bytes);

        System.out.println("persionDeserializer.getName:"+persionDeserializer.getName());


    }



}
