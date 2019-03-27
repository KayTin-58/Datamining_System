package com.zhang.myweka.classify.impl;

import com.zhang.dto.EvaluationAndClassNameAndClassModel;
import com.zhang.entity.parameter.J48Param;
import com.zhang.exception.TypeException;
import com.zhang.myweka.classify.MyJ48;
import org.springframework.stereotype.Service;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Debug;
import weka.core.Instances;

import java.util.Random;

/**
 * Created by 直到世界尽头 on 2018/4/5.
 */
@Service
public class MyJ48Impl implements MyJ48{


    /**
     * 测试集验证
     * @param instances
     * @param test
     * @param index
     * @param j48Param
     * @throws Exception
     */
    @Override
    public void verificationByTest(Instances instances, Instances test, Integer index,J48Param j48Param) throws Exception {

    }


    /**
     * 单次十折交叉验证
     * @param instances
     * @param folds
     * @throws Exception
     */
    @Override
    public EvaluationAndClassNameAndClassModel verificationByCross(Instances instances, Integer folds, Integer index, J48Param j48Param) throws Exception {

        //设置类别属性
        if(index>0&index<instances.numAttributes()-1){
            instances.setClassIndex(index);
        }else{
            //默认最后一个属性为类别属性
            instances.setClassIndex(instances.numAttributes()-1);
        }

        if(!instances.classAttribute().isNominal()){
            throw new TypeException("J48暂时不支持类别属性为非标称属性的数据集！");
        }

        //分类器
        J48 j48=new J48();
        if(j48Param==null){
            String[] options=new String[2];
            options[0]="-C";
            options[1]="0.25";
            j48.setOptions(options);
        }else{
            j48.setBatchSize(j48Param.getM_BatchSize());
            j48.setConfidenceFactor(j48Param.getM_ConfidenceFactor());
            j48.setMinNumObj(j48Param.getM_MinNumObj());
            j48.setNumDecimalPlaces(j48Param.getM_NumDecimalPlaces());
            j48.setNumFolds(j48Param.getM_NumFolds());
        }

        //执行交叉验证
            int seed=8907;//随机种子
            Random random=new Debug.Random(seed);
            Instances newData=new Instances(instances);
            newData.randomize(random);

            //如果类别为标称型 则根据其类别值进行分层
            if(newData.classAttribute().isNominal()){
                newData.stratify(folds);
            }
            //执行交叉验证
            Evaluation evaluation=new Evaluation(newData);
             Classifier clsCopy=null;
            for(int j=0;j<folds;j++){
                //newData.randomize(random);
                //训练集
                Instances train=newData.trainCV(folds,j);
                //测试集
                Instances test=newData.testCV(folds,j);
                //构建并评估分类器
                 clsCopy= AbstractClassifier.makeCopy(j48);
                 clsCopy.buildClassifier(train);
                 evaluation.evaluateModel(clsCopy,test);
        }
        //clsCopy.buildClassifier(instances);
        // System.out.println(clsCopy);
        //System.out.println( new J48Result(new EvaluationAndClassName(evaluation,"J48")));
        return new EvaluationAndClassNameAndClassModel(evaluation,"J48",clsCopy);
    }
}
