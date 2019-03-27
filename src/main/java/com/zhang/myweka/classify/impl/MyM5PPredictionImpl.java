package com.zhang.myweka.classify.impl;

import com.zhang.exception.HeadersNotequealException;
import com.zhang.myweka.classify.MyM5PPrediction;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.trees.M5P;
import weka.core.Attribute;
import weka.core.Instances;

/**
 * Created by 直到世界尽头 on 2018/4/20.
 */

@Service
public class MyM5PPredictionImpl implements MyM5PPrediction {


    /**
     *
     * @param ukInstances
     * @param classifier
     * @return
     * @throws Exception
     */
    @Override
    public Instances Prediction(Instances ukInstances, Classifier classifier, Instances train) throws Exception {

        Attribute class_atr=train.classAttribute();
        ukInstances.setClass(class_atr);
        class_atr=null;
        if(!train.equalHeaders(ukInstances)){
            throw new HeadersNotequealException("待预测数据类型和当前模型类型不匹配！");
        }
        Instances u=ukInstances;
        //第一步：检测模型类型
        if((classifier instanceof M5P)){
            M5P m5PPre_Model=(M5P)classifier;
            for(int i=0;i<u.numInstances();i++){
                double value=m5PPre_Model.classifyInstance(u.get(i));//得到输出值
                u.get(i).setClassValue(value);
                System.out.println("m5p:"+value);
            }
         return u;
        }
        return null;
    }
}
