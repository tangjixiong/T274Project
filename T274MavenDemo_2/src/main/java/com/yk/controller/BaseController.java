package com.yk.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试远程代码提交
 */
public class BaseController {
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        System.out.println("=================initBinder=====================");
        dataBinder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
    }


}
