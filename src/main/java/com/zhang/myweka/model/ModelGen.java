package com.zhang.myweka.model;

import weka.classifiers.Classifier;

import java.io.InputStream;

/**
 * Created by 直到世界尽头 on 2018/4/12.
 */
public interface ModelGen {


    /**
     * 保存模型
     * @param classifier
     * @throws Exception
     */
    public InputStream SaveModel(Classifier classifier, String modelName)throws Exception;


    /**
     * 读取模型
     */
    public void ReadModel();
}
