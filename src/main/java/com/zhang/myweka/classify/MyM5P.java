package com.zhang.myweka.classify;

import com.zhang.dto.EvaluationAndClassNameAndClassModel;
import com.zhang.entity.parameter.MyParam;
import weka.core.Instances;

/**
 * Created by 直到世界尽头 on 2018/4/4.
 */
public interface MyM5P {


    /**
     * 构建通过测试机验证的线性回归分析
     * @param instances
     * @throws Exception
     */
    public void verificationByTest(Instances instances,Integer folds)throws Exception;


    /**
     * 交叉验证
     * @param instances
     * @param folds
     * @param index
     * @param myParam
     * @throws Exception
     */
    public EvaluationAndClassNameAndClassModel verificationByCross(Instances instances, Integer folds, int index, MyParam myParam)throws  Exception;

}
