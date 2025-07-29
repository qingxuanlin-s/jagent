package com.xyz.code.base;

public class ExceptionPoolInfo {

    public static void main(String[] args) throws Exception {
        try {
            int a = 10 / 0;
        }finally {
            throw new Exception("Exception in finally block");
        }
//        catch (Exception e){
//            System.out.println("Exception caught");
//        }
    }


}
