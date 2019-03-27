package com.zhang.myweka.classify;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 *
 * M5P的预测
 * Created by 直到世界尽头 on 2018/4/20.
 */
public interface MyM5PPrediction {


    /**
     * 使用M5P模型进行数据预测
     * @param ukInstances
     * @param classifier
     * @throws Exception
     */
    public Instances Prediction(Instances ukInstances, Classifier  classifier,Instances train)throws Exception;
}
