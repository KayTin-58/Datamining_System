package com.zhang.myweka.attribute_op;

import com.zhang.dto.NumericOfValueEntity;
import com.zhang.dto.StrAndNominalOfValueListEntity;
import com.zhang.entity.AttributeAnalyEntity;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 直到世界尽头 on 2018/3/16.
 */
public interface AttributeInterface {

    public AttributeAnalyEntity attributeAnaly2Entity(Instances instances,String attributeName,int index);


    /**
     *
     * @param instances
     * @param attributeName
     * @param index
     * @return
     */
    public StrAndNominalOfValueListEntity getAttributeValuesWhenStrOrNomi(Instances instances,String attributeName,int index);


    /**
     * 获取数据类型
     * @param instances
     * @param attributeName
     * @param index
     * @return
     */
    public String getAttributeType(Instances instances,String attributeName,int index);

    /**
     * 当数据类型是
     * @param instances
     * @param attributeName
     * @param index
     * @return
     */
    public NumericOfValueEntity getAttributeValueWhenNumer(Instances instances,String attributeName,int index);



    public HashMap<String,Integer> statistics_valueAndClass(Instances instances, String attribute_name, int class_index);


    /**
     * 获取标称型属性集合
     * @param instances
     * @return
     */
    public ArrayList<String> getAttributr_List(Instances instances);
}
