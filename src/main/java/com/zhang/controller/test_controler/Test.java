package com.zhang.controller.test_controler;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 直到世界尽头 on 2018/3/6.
 */
@RestController
@RequestMapping(value="/test01")
@Scope("session")
public class Test {
    volatile Integer num=0;

    @RequestMapping(value="/add")
    public void addNum(Integer num){

        this.num=num;
        System.out.print("当前值为："+this.num);
    }


    @RequestMapping(value="/get")
    public Integer getNum(HttpServletRequest request)throws  Exception{
        return this.num;
    }

    @RequestMapping(value="/go2add")
    public ModelAndView goAdd(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("add");
        return modelAndView;
    }

    @RequestMapping(value="/go2get")
    public ModelAndView goGet(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("GET");
        return modelAndView;
    }

    @RequestMapping(value="/test")
    public ModelAndView gotest(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("test");
        return modelAndView;
    }

    @RequestMapping(value = "/main")
    public  ModelAndView go2Upload(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("treeTest");
        return  modelAndView;
    }


    @RequestMapping(value = "/chart")
    public  ModelAndView go2chart(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("first_chart");
        return  modelAndView;
    }
}
