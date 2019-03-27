package com.zhang.controller;

import com.zhang.utils.GetRequestiP;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 直到世界尽头 on 2018/3/18.
 */


@RestController
@RequestMapping(value = "/base")
@Scope("session")
public class BaseController {

     protected String ip;
     protected String fileName=null;
     protected String uniqueID="";
    // protected HttpSession session;


     @ModelAttribute
     public void init(HttpServletRequest request){
          ip=GetRequestiP.getIpAddr(request);
          System.out.println("访问ip:"+ip+"---uniqueID："+uniqueID);
     }
}
