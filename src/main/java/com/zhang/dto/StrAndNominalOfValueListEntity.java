package com.zhang.dto;

import com.zhang.entity.AttributeAnalyEntity;
import com.zhang.entity.StringAndNominalofValueEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 直到世界尽头 on 2018/3/16.
 * 每个属性对应的信息集
 */
public class StrAndNominalOfValueListEntity  extends AttributeAnalyEntity implements Serializable{

    public StrAndNominalOfValueListEntity(AttributeAnalyEntity attributeAnalyEntity){
        this.setWeight(attributeAnalyEntity.getWeight());
        this.setDistinct(attributeAnalyEntity.getDistinct());
        this.setUnique(attributeAnalyEntity.getUnique());
        this.setMissing(attributeAnalyEntity.getMissing());
        this.setAttribute_Name(attributeAnalyEntity.getAttribute_Name());
        this.setMissing_percentage(attributeAnalyEntity.getMissing_percentage());
        this.setNum_type(attributeAnalyEntity.getNum_type());
    }



    private ArrayList<StringAndNominalofValueEntity> list;

    public ArrayList<StringAndNominalofValueEntity> getList() {
        return list;
    }

    public void setList(ArrayList<StringAndNominalofValueEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "StrAndNominalOfValueListEntity{" +
                "list=" + list +
                '}';
    }
}
