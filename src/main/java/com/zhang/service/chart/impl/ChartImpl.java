package com.zhang.service.chart.impl;

import com.zhang.exception.TypeException;
import com.zhang.service.chart.ChartInterface;
import com.zhang.service.chart.data.BT_Series;
import com.zhang.service.chart.data.Series;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instance;
import weka.core.Instances;

import java.util.*;

/**
 * Created by 直到世界尽头 on 2018/4/19.
 */
@Service
public class ChartImpl implements ChartInterface {


    @Override
    public ArrayList<Series>  getInforMation(String attributeName, Integer index, Instances instances) throws Exception {

        Attribute attribute1=null;
//        try {
//            attribute1= instances.classAttribute();
//        } catch (UnassignedClassException e) {
//            e.printStackTrace();
//        }finally {
//
//        }
        instances.setClassIndex(instances.numAttributes()-1);
        attribute1=instances.classAttribute();
        Attribute attribute=instances.attribute(attributeName);


        if(!attribute1.isNominal()||!attribute.isNominal()){
            throw new TypeException("数据类型不符合要求！");
        }

        //第一步：根据属性名获取属性相关信息

        Enumeration<Object> enumeration=attribute.enumerateValues();
        ArrayList<String> attribute_list=new ArrayList<>();//属性值集合
        while (enumeration.hasMoreElements()){
            attribute_list.add((String)enumeration.nextElement());
        }
        //第二步：获取分类属性相关信息

        Enumeration<Object>class_values=attribute1.enumerateValues();
        ArrayList<String> class_list=new ArrayList<>();
        while (class_values.hasMoreElements()){
            class_list.add((String)class_values.nextElement());
        }

        Attribute attribute2=instances.classAttribute();

        int[] integers=null;
        HashMap<String,int[]> hashMap=new HashMap<>();
        int size=class_list.size();

        //第三步：遍历数据集合
        for(int i=0;i<instances.numInstances();i++){
            Instance instance=instances.instance(i);
            String value=instance.stringValue(attribute);
            String class_value=instance.stringValue(attribute2);

            if("?".equals(value)||"?".equals(class_value)){
                continue;
            }
            int index1=class_list.indexOf(class_value);//当前类别值的下标
            if(hashMap.containsKey(value)){
                integers=hashMap.get(value);
                integers[index1]++;
                hashMap.put(value,integers);
            }else{
                integers=new int[size];
                //System.out.println("哦："+integers.length);
                integers[index1]++;
                hashMap.put(value,integers);
            }
        }

        ArrayList<Series> arrayList=new ArrayList<>();
        Iterator iterator=hashMap.entrySet().iterator();
        Series series=null;
      while (iterator.hasNext()){
          series=new Series();
          Map.Entry<String, int[]> entry = (Map.Entry) iterator.next();
          String name=entry.getKey();
          int[] ints=entry.getValue();
          series.setName(name);
          series.setData(ints);
          arrayList.add(series);
      }
      for(int i=0;i<arrayList.size();i++){
          System.out.println(arrayList.get(i));
      }

      return arrayList;
    }


    @Override
    public ArrayList<BT_Series> getInfor_BT(String attributeName, Integer index, Instances instances) throws Exception {

        int sum=instances.numInstances();//总数

        Attribute attribute1=null;
        instances.setClassIndex(instances.numAttributes()-1);
        attribute1=instances.classAttribute();
        Attribute attribute=instances.attribute(attributeName);
        if(!attribute1.isNominal()||!attribute.isNominal()){
            throw new TypeException("数据类型不符合要求！");
        }

        AttributeStats attributeStats=instances.attributeStats(attribute.index());
        int length=attributeStats.nominalCounts.length;
        ArrayList arrayList=new ArrayList<BT_Series>();
        BT_Series bt_series=null;
        for(int i=0;i<length;i++){
           bt_series=new BT_Series();
            String name= attribute.value(i);
            int y=attributeStats.nominalCounts[i];
            bt_series.setName(name);
            bt_series.setY(y);
            arrayList.add(bt_series);
        }
        return arrayList;
    }

    @Override
    public ArrayList<String> getClass_value(Instances instances) throws Exception {
        instances.setClassIndex(instances.numAttributes()-1);
        ArrayList<String> arrayList=new ArrayList<>();
        Enumeration<Object> enumeration=instances.classAttribute().enumerateValues();
        while (enumeration.hasMoreElements()){
            String value=(String)enumeration.nextElement();
            arrayList.add(value);
        }
        return arrayList;
    }
}
