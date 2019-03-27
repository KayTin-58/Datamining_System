package com.zhang.myweka.classify;

import com.zhang.dto.EvaluationAndClassNameAndClassModel;
import com.zhang.entity.parameter.J48Param;
import weka.core.Instances;

/**
 * Created by 直到世界尽头 on 2018/4/5.
 */
public interface MyJ48 {

    /**
     *
     * @param instances
     * @throws Exception
     */
    public void verificationByTest(Instances instances, Instances test, Integer index,J48Param j48Param)throws Exception;


    /**
     *
     * @param instances
     * @param folds
     * @param index
     * @param j48Param
     * @return
     * @throws Exception
     */
    public EvaluationAndClassNameAndClassModel verificationByCross(Instances instances, Integer folds, Integer index, J48Param j48Param)throws  Exception;

}
