package com.zhang.myweka.classify;

import com.zhang.entity.parameter.J48Param;
import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Created by 直到世界尽头 on 2018/4/9.
 */
public interface MyJ48Prediction {

    /**
     * 交叉验证并预测
     * @param instances
     * @param folds
     * @param index
     * @param j48Param
     * @param ukInstances
     */
    public Instances CVPrediction(Instances instances, Integer folds, Integer index, J48Param j48Param, Instances ukInstances)throws Exception;


    /**
     * 使用模型进行预测
     * @param ukinstances
     * @param classifier
     * @return
     * @throws Exception
     */
    public Instances PredictionByModel(Instances ukinstances, Classifier classifier,Instances train)throws Exception;

    /**
     * 使用过滤器的模型预测
     * @param ukinstances
     * @param classifier
     * @param train
     * @return
     * @throws Exception
     */
    public Instances PredictionByModelWithClassFication(Instances ukinstances,Classifier classifier,Instances train)throws Exception;
}
