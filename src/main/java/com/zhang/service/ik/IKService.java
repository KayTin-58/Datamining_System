package com.zhang.service.ik;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by 直到世界尽头 on 2018/4/21.
 */
public interface IKService {


    /**
     * 解析传入的文件内容
     * @param fileName  文件名
     * @return Map.key :关键字   Map.value:出现的次数
     * @throws Exception
     */
    public HashMap<String,Integer> getInfoMap(String fileName)throws Exception;


    /**
     *  解析传入的文件内容
     * @param inputStream 文件的输入流
     * @return Map.key :关键字   Map.value:出现的次数
     * @throws Exception
     */
    public HashMap<String,Integer> getInfoMap_01(InputStream inputStream)throws  Exception;

    public HashMap<String,Integer> getInfoMap_01(String text)throws  Exception;
    /**
     * 返回用户指定的字集出现的次数
     * @param inputStream
     * @param hashSet
     * @return
     * @throws Exception
     */
    public HashMap<String,Integer> getInfoMap_02(InputStream inputStream, HashSet<String> hashSet)throws Exception;


    public String file2text(InputStream fileInputStream)throws Exception;
}
