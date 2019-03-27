package com.zhang.dto;

import com.zhang.entity.IKEntity;
import com.zhang.entity.ResponseInfoEntity;

import java.util.*;

/**
 * Created by 直到世界尽头 on 2018/4/21.
 */
public class IKResult extends ResponseInfoEntity {
    private String text;
    private ArrayList<IKEntity> list;



    public IKResult(String text, HashMap<String,Integer> hashMap,boolean flag) {
        this.text = text;
        ArrayList<IKEntity> arrayList=new ArrayList<>();
        Iterator  ikResultIterator=hashMap.entrySet().iterator();
        IKEntity ikEntity=null;
        if(flag){
            while(ikResultIterator.hasNext()){
                Map.Entry entry = (Map.Entry) ikResultIterator.next();
                String key=(String)entry.getKey();
                Integer count=(Integer)entry.getValue();
                if(count<3){
                    continue;
                }
                ikEntity=new IKEntity(key,count);
                arrayList.add(ikEntity);
            }
        }else{
            while(ikResultIterator.hasNext()){
                Map.Entry entry = (Map.Entry) ikResultIterator.next();
                String key=(String)entry.getKey();
                Integer count=(Integer)entry.getValue();
                ikEntity=new IKEntity(key,count);
                arrayList.add(ikEntity);
            }
        }

        //按照统计数大小排序
        Collections.sort(arrayList);
        this.list=arrayList;
    }

    public IKResult(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<IKEntity> getList() {
        return list;
    }

    public void setList(ArrayList<IKEntity> list) {
        this.list = list;
    }


}
