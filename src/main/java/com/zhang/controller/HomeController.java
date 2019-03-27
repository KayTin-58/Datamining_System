package com.zhang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 直到世界尽头 on 2018/3/10.
 * 总路由器
 */
@RestController
@RequestMapping(value="/Home")
public class HomeController {


    /**
     * 转向登录页面
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView go2Login(){
        ModelAndView modelAndView=new ModelAndView();
        return  modelAndView;
    }


    /**
     * 转向主页面
     * @return
     */
    @RequestMapping(value = "/main")
    public  ModelAndView go2Upload(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("main");
        return  modelAndView;
    }



}
