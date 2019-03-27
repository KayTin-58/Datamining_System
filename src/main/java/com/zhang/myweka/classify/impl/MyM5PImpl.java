package com.zhang.myweka.classify.impl;

import com.zhang.dto.EvaluationAndClassNameAndClassModel;
import com.zhang.entity.parameter.MyParam;
import com.zhang.exception.TypeException;
import com.zhang.myweka.classify.MyM5P;
import org.springframework.stereotype.Service;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.M5P;
import weka.core.Debug;
import weka.core.Instances;

import java.util.Random;

/**
 * Created by 直到世界尽头 on 2018/4/4.
 */
@Service
public class MyM5PImpl implements MyM5P {


    /**
     * 交叉验证
     * @param instances
     * @param folds
     * @param index
     * @param myParam
     * @throws Exception
     */
    @Override
    public EvaluationAndClassNameAndClassModel verificationByCross(Instances instances, Integer folds, int index, MyParam myParam) throws Exception {
        //第一步：判断类别属性
        if(index>0&index<instances.numInstances()-1){
            instances.setClassIndex(index);
        }else{
            //默认最后一列为类别属性
            instances.setClassIndex(instances.numAttributes()-1);
        }

        //判断数据类型书否是M5可以支持的
        if(!instances.classAttribute().isNumeric()){
            throw new TypeException("M5P不支持类别属性为非数字型的数据集！");
        }

        M5P m5P=new M5P();
        if(myParam!=null){

        }else{

        }
       //执行交叉验证
        int seed=8907;//随机种子
        Random random=new Debug.Random(seed);
        Instances newData=new Instances(instances);

        newData.randomize(random);
        Evaluation evaluation=new Evaluation(newData);
        Classifier clsCopy=null;

        for(int j=0;j<folds;j++){
            //训练集
            Instances train=newData.trainCV(folds,j);
            //测试集
            Instances test=newData.testCV(folds,j);
            //构建并评估分类器
            clsCopy= AbstractClassifier.makeCopy(m5P);
            clsCopy.buildClassifier(train);
            evaluation.evaluateModel(clsCopy,test);
        }
        return new EvaluationAndClassNameAndClassModel(evaluation,"M5P",clsCopy);

    }

    @Override
    public void verificationByTest(Instances instances,Integer folds) throws Exception {

    }
}
