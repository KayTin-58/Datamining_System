package com.zhang.myweka.attribute_op.impl;

import com.zhang.dto.NumericOfValueEntity;
import com.zhang.dto.StrAndNominalOfValueListEntity;
import com.zhang.entity.AttributeAnalyEntity;
import com.zhang.entity.StringAndNominalofValueEntity;
import com.zhang.myweka.attribute_op.AttributeInterface;
import org.springframework.stereotype.Service;
import weka.core.*;

import java.text.NumberFormat;
import java.util.*;


/**
 * Created by 直到世界尽头 on 2018/3/16.
 */
@Service
public class AttributeImpl implements AttributeInterface {


    @Override
    public String getAttributeType(Instances instances, String attributeName, int index) {
        Attribute attribute=instances.attribute(attributeName);
        String num_type=null;
        if(attribute.isDate()){
            num_type="Date";
        }else if(attribute.isNominal()){
            num_type="Nominal";
        }else if(attribute.isNumeric()){
            num_type="Numeric";
        }else if(attribute.isString()){
            num_type="String";
        }else{
            num_type="unknow";
        }

        return num_type;
    }

    @Override
    public AttributeAnalyEntity attributeAnaly2Entity(Instances instances,String attributeName,int index) {


        if(instances==null&attributeName==null&index<0){
            return null;
        }

        //String name=instances.attribute(index).name();
        //System.out.println("属性名："+name);


        AttributeAnalyEntity attributeAnalyEntity=new AttributeAnalyEntity();

        attributeAnalyEntity.setAttribute_Name(attributeName);

        Attribute attribute=instances.attribute(attributeName);




        String num_type=null;
        num_type=getAttributeType(instances,attributeName,index);
        attributeAnalyEntity.setNum_type(num_type);


        AttributeStats attributeStats=instances.attributeStats(index);
        int intCount=attributeStats.intCount;
        int missingCount=attributeStats.missingCount;





        attributeAnalyEntity.setMissing(missingCount);
        String missing_percentage_s=null;
        if(instances.numInstances()!=0){

            double missing_percentage = ((double)missingCount)/instances.numInstances();
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            missing_percentage_s=nf.format(missing_percentage);
        }

        attributeAnalyEntity.setMissing_percentage(missing_percentage_s);
        int distinctCount=attributeStats.distinctCount;
        attributeAnalyEntity.setDistinct(distinctCount);
        int uniqueCount=attributeStats.uniqueCount;
        attributeAnalyEntity.setUnique(uniqueCount);
        double weight=attribute.weight();
        attributeAnalyEntity.setWeight(weight);


        System.out.println("属性分析结果："+attributeAnalyEntity.toString());
        return attributeAnalyEntity;
    }





    @Override
    public StrAndNominalOfValueListEntity getAttributeValuesWhenStrOrNomi(Instances instances, String attributeName, int index) {

        StrAndNominalOfValueListEntity strAndNominalOfValueListEntity=
                 new StrAndNominalOfValueListEntity(this.attributeAnaly2Entity( instances,  attributeName,  index));

        ArrayList<StringAndNominalofValueEntity>  arrayList=new ArrayList<>();

        //属性值的统计信息
        StringAndNominalofValueEntity stringAndNominalofValueEntity=null;

        Attribute attribute=instances.attribute(attributeName);
        AttributeStats attributeStats=instances.attributeStats(index);

        HashMap<String,Integer> hashMap=this.statistics_valueAndClass(instances,attributeName,index);

        if(attributeStats.nominalCounts!=null){
            int value_length=attributeStats.nominalCounts.length;//属性值的数量
            for(int i = 0; i < value_length; i++){
                stringAndNominalofValueEntity=new StringAndNominalofValueEntity();

                String lable=attribute.value(i);

                stringAndNominalofValueEntity.setLable(lable);
                stringAndNominalofValueEntity.setCount(attributeStats.nominalCounts[i]);
                stringAndNominalofValueEntity.setWeight(Utils.doubleToString(attributeStats.nominalWeights[i], 3));


                if(instances.attribute(instances.numAttributes()-1).isNominal()){
                    ArrayList<String> statistics_value_list=new ArrayList<>();
                    //重点开发
                    Enumeration<Object> enumeration=instances.attribute(instances.numAttributes()-1).enumerateValues();
                    while (enumeration.hasMoreElements()){
                        String class_value=(String)enumeration.nextElement();
                        String key=lable+"-"+class_value;
                        Integer count=hashMap.get(key)==null? 0:hashMap.get(key);
                        String value=class_value+":"+count;
                        statistics_value_list.add(value);
                    }
                    stringAndNominalofValueEntity.setStatistics_value_list(statistics_value_list);
                }
                arrayList.add(stringAndNominalofValueEntity);
            }
            strAndNominalOfValueListEntity.setList(arrayList);
        }
        return strAndNominalOfValueListEntity;
    }


    @Override
    public NumericOfValueEntity getAttributeValueWhenNumer(Instances instances, String attributeName, int index) {
        NumericOfValueEntity numericOfValueEntity=new NumericOfValueEntity(this.attributeAnaly2Entity( instances,  attributeName,  index));
        Attribute attribute=instances.attribute(attributeName);
        AttributeStats attributeStats=instances.attributeStats(index);


        String max=Utils.doubleToString(attributeStats.numericStats.max, 3);
        String min=Utils.doubleToString(attributeStats.numericStats.min, 3);
        String mean=Utils.doubleToString(attributeStats.numericStats.mean, 3);
        String StdDev=Utils.doubleToString(attributeStats.numericStats.stdDev, 3);

        numericOfValueEntity.setMaxValue(max);
        numericOfValueEntity.setMinValue(min);
        numericOfValueEntity.setMean(mean);
        numericOfValueEntity.setStdDev(StdDev);

        //System.out.println("numericOfValueEntity"+numericOfValueEntity.toString());
        return numericOfValueEntity;
    }


    /**
      * 目的：默认最后一个属性为分类属性（）
     * outlook      play.no     play.yes
     *  sunny         3             2
     *  rain          4              0
     * @param instances
     * @param attributeName
     */
    @Override
    public HashMap<String,Integer> statistics_valueAndClass(Instances instances,String attributeName,int class_index) {

               HashMap<String, Integer> hashMap = null;

              if(attributeName!=null){

                  Attribute attribute=instances.attribute(attributeName);
                  Enumeration<Object> enumeration=attribute.enumerateValues();
                  StringBuilder stringBuilder=new StringBuilder();
                  while (enumeration.hasMoreElements()){
                      stringBuilder.append((String)enumeration.nextElement());
                  }
                  if(attribute.isNominal()&&instances.attribute(instances.numAttributes()-1).isNominal()){

                      hashMap= new HashMap<>();

                      //开始遍历数据集合
                      for (int i = 0; i < instances.size(); i++) {
                          Instance instance = instances.instance(i);
                          String attribute_value = instance.stringValue(attribute);
                          //instance.classAttribute()
                          String class_value = instance.stringValue(instance.numAttributes()-1);

                          String key = attribute_value + "-" + class_value;
                          if(stringBuilder.toString().contains(key)){
                              continue;
                          }
                          if (hashMap.containsKey(key)) {
                              int count = hashMap.get(key);
                              count++;
                              hashMap.put(key, count);
                          } else {
                              hashMap.put(key, 1);
                          }
                      }

                      Iterator iter = hashMap.entrySet().iterator();
                      while (iter.hasNext()) {
                          Map.Entry<String, Integer> entry = (Map.Entry) iter.next();
                          String key_01 = entry.getKey();
                          Integer value_01 = entry.getValue();
//                          System.out.println(key_01 + "    " + value_01);
                      }
                  }
              }

              return hashMap;
            }


    @Override
    public ArrayList<String> getAttributr_List(Instances instances) {
        Enumeration<Attribute> enumeration=instances.enumerateAttributes();
        ArrayList<String> arrayList=new ArrayList<>();
        Attribute attribute=null;
        while(enumeration.hasMoreElements()){
            attribute=enumeration.nextElement();
            if(attribute.isNominal()){
                arrayList.add(attribute.name());
            }
        }
        return arrayList;
    }
}

