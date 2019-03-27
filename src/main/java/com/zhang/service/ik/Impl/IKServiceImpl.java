package com.zhang.service.ik.Impl;

import com.zhang.service.ik.IKService;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by 直到世界尽头 on 2018/4/21.
 */
@Service
public class IKServiceImpl implements IKService {
    @Override
    public HashMap<String, Integer> getInfoMap(String fileName) throws Exception {
        return null;
    }


    @Override
    public HashMap<String, Integer> getInfoMap_01(String text) throws Exception {
        StringReader stringReader=new StringReader(text);
        IKSegmenter ik = new IKSegmenter(stringReader, true);
        Lexeme lex;
        HashMap<String,Integer> hashMap=new HashMap<>();
        String key=null;
        while((lex= ik.next())!=null){
            key=lex.getLexemeText();
            if(key.length()<2){
                continue;
            }
            if(hashMap.containsKey(key)){
                Integer value=hashMap.get(key);
                value++;
                hashMap.put(key,value);
            }else{
                int value=1;
                hashMap.put(key,value);
            }
        }

        return hashMap;
    }

    @Override
    public HashMap<String, Integer> getInfoMap_01(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"GBK");
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        IKSegmenter ik = new IKSegmenter(bufferedReader, true);
        Lexeme lex;
        String key=null;
        HashMap<String,Integer> hashMap=new HashMap<>();
        while((lex= ik.next())!=null){
            key=lex.getLexemeText();
            if(key.length()<2){
                continue;
            }
            if(hashMap.containsKey(key)){
                Integer value=hashMap.get(key);
                value++;
                hashMap.put(key,value);
            }else{
                int value=1;
                hashMap.put(key,value);
            }
        }
        return hashMap;
    }


    /**
     * 给定目标词集合
     * @param inputStream
     * @param hashSet
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String, Integer> getInfoMap_02(InputStream inputStream, HashSet<String> hashSet) throws Exception {
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"GBK");
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        IKSegmenter ik = new IKSegmenter(bufferedReader, true);
        Lexeme lex;
        String key=null;
        HashMap<String,Integer> hashMap=new HashMap<>();
        while((lex=ik.next())!=null){
            key=lex.getLexemeText();
            if(key.length()<2||!hashSet.contains(key)){
                continue;
            }
            if(hashMap.containsKey(key)){
                int a=hashMap.get(key);
                a++;
                hashMap.put(key,a);
            }else{
                int value=1;
                hashMap.put(key,value);
            }
        }
        return hashMap;
    }


    @Override
    public String file2text(InputStream fileInputStream) throws Exception {

        InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line;
        StringBuffer stringBuffer=new StringBuffer();
        while ((line = br.readLine()) != null) {
            stringBuffer.append(line);
        }
        br.close();
        inputStreamReader.close();
        return stringBuffer.toString();
    }
}
