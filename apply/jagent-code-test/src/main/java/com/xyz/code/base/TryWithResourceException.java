package com.xyz.code.base;

import java.io.FileOutputStream;

public class TryWithResourceException {


    public static void main(String[] args) {
        try (FileOutputStream fos = new FileOutputStream("output.txt")) {
            fos.write("Hello World".getBytes());
            throw new RuntimeException("Something went wrong");
        } catch (Exception e) {

            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
