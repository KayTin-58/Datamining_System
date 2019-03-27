package com.zhang.myweka.classify.impl;/**
 * Created by 直到世界尽头 on 2018/4/9.
 */

import com.zhang.entity.parameter.J48Param;
import com.zhang.exception.HeadersNotequealException;
import com.zhang.exception.TypeException;
import com.zhang.myweka.classify.MyJ48Prediction;
import org.springframework.stereotype.Service;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AddClassification;

import java.util.Random;

@Service
public class MyJ48PredictionImpl implements MyJ48Prediction {


    /**
     * 执行交叉验证并预测
     * @param instances  训练数据
     * @param folds 折数
     * @param index 类别属性下标
     * @param j48Param  参数
     * @param ukInstances  待预测数据
     * @throws Exception
     */
    @Override
    public Instances CVPrediction(Instances instances, Integer folds, Integer index, J48Param j48Param, Instances ukInstances) throws Exception{


        //设置类别属性
        if(index>0&index<instances.numAttributes()-1){
            instances.setClassIndex(index);
            ukInstances.setClassIndex(index);
        }else{
            //默认最后一个属性为类别属性
            instances.setClassIndex(instances.numAttributes()-1);
            ukInstances.setClassIndex(ukInstances.numAttributes()-1);
        }
        //        //判断训练数据和待测试数据类型是否相同
        if(!instances.equalHeaders(ukInstances)){
            //测试数据类型和预测数据类型不一样
           throw new HeadersNotequealException("测试数据类型和待预测数据类型不一致！！！");
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
        Instances pred=null;
        for(int i=0;i<folds;i++){
            //训练集
            Instances train=newData.trainCV(folds,i);
            //测试集
            Instances test=newData.testCV(folds,i);
            //构建并评估分类器
            clsCopy= AbstractClassifier.makeCopy(j48);
            clsCopy.buildClassifier(train);
            evaluation.evaluateModel(clsCopy,test);

            //添加预测
            AddClassification addClassification=new AddClassification();
            addClassification.setClassifier(j48);
            addClassification.setOutputClassification(true);
            addClassification.setOutputDistribution(true);
            addClassification.setOutputErrorFlag(true);
            addClassification.setInputFormat(train);
            //System.out.print("train:"+train.numInstances());
            //训练分类器
            Filter.useFilter(train,addClassification);
            //在集上进行预测
            pred=Filter.useFilter(ukInstances,addClassification);
            //输出评估结果
           // System.out.println(pred);

            //分类器是否实现OptionHandler接口
        }
       // System.out.print(pred.toString());
        return pred;
    }


    /**
     * 未完成
     * @param ukinstances   结果集
     * @param classifier
     * @return
     * @throws Exception
     */
    @Override
    public Instances PredictionByModel(Instances ukinstances, Classifier classifier,Instances train) throws Exception {

        Attribute classAttribute=train.classAttribute();
        ukinstances.setClass(classAttribute);
        classAttribute=null;//gc
        if(!ukinstances.equalHeaders(train)){
            throw new HeadersNotequealException("测试数据类型和待预测数据类型不一致！！！");
        }
        for(int i=0;i<ukinstances.numInstances();i++){
            double pre=classifier.classifyInstance(ukinstances.instance(i));
            double[] dis=classifier.distributionForInstance(ukinstances.instance(i));
            double classValue = ukinstances.instance(i).classValue();
           // System.out.println(pre+"--"+classValue+"--"+train.classAttribute().value((int)pre)+"--"+ Arrays.toString(dis));
//            System.out.println("Message classified as : " +
//                    train.classAttribute().value((int)pre));
            ukinstances.instance(i).setClassValue(train.classAttribute().value((int)pre));
        }
        //System.out.println(ukinstances);
        return ukinstances;
    }


    /**
     * 废弃
     * @param ukinstances
     * @param classifier
     * @param train
     * @return
     * @throws Exception
     */
    @Override
    public Instances PredictionByModelWithClassFication(Instances ukinstances, Classifier classifier, Instances train) throws Exception {

        Attribute classAttribute=train.classAttribute();
        ukinstances.setClass(classAttribute);
        classAttribute=null;
        if(!ukinstances.equalHeaders(train)){
            throw new HeadersNotequealException("测试数据类型和待预测数据类型不一致！！！");
        }
        //添加预测
        AddClassification addClassification=new AddClassification();
        addClassification.setClassifier(classifier);
        addClassification.setOutputClassification(true);
        addClassification.setOutputDistribution(true);
        //addClassification.setOutputErrorFlag(true);
        addClassification.setInputFormat(ukinstances);
        Instances instances=Filter.useFilter(ukinstances,addClassification);
        //System.out.println(instances);
        return instances;
    }
}
