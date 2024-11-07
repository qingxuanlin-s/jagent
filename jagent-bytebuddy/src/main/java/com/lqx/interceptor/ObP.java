package com.lqx.interceptor;

import com.lqx.VMUtil;

import java.util.Map;

public class ObP {

    public void getO(){
        System.out.println(this.getClass().getName());
        System.out.println(VMUtil.jstack("lqx"));
    }
}
