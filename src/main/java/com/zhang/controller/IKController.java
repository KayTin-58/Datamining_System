package com.zhang.controller;

import com.zhang.dto.IKResult;
import com.zhang.service.ik.IKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * Created by 直到世界尽头 on 2018/4/21.
 */

@RestController
@RequestMapping(value = "/ik_controller")
public class IKController extends BaseController {

    @Autowired
    IKService ikService;
    @RequestMapping(value = "/fileupload")
    @ResponseBody
    public IKResult getFile2Text(MultipartFile file){
        String text="";
        HashMap hashMap=new HashMap();
        IKResult ikResult=null;
        try {
            text=ikService.file2text(file.getInputStream());
            hashMap=ikService.getInfoMap_01(text);
            System.out.println(text);
            ikResult=new IKResult(text,hashMap,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ikResult;
    }
}
