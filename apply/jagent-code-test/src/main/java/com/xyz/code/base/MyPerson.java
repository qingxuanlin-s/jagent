package com.xyz.code.base;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MyPerson {

    private FileOutputStream outputStream = new FileOutputStream("output.txt");

    public MyPerson() throws FileNotFoundException {
    }
}
