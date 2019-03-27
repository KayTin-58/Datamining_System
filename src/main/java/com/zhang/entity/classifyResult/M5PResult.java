package com.zhang.entity.classifyResult;

import com.zhang.dto.EvaluationAndClassNameAndClassModel;
import com.zhang.entity.PredicInfo;
import com.zhang.entity.ResponseInfoEntity;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.M5P;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 直到世界尽头 on 2018/4/13.
 */
public class M5PResult extends ResponseInfoEntity implements Serializable{

    public M5PResult(){}
    public M5PResult(EvaluationAndClassNameAndClassModel evaluationAndClassNameAndClassModel){

        if(evaluationAndClassNameAndClassModel!=null){
            Evaluation evaluation=evaluationAndClassNameAndClassModel.getEvaluation();
            try {
              //  System.out.println(evaluation.toCumulativeMarginDistributionString());
                M5P m5P=(M5P)evaluationAndClassNameAndClassModel.getClassifier();
               // System.out.println(m5P.graph());
                this.summay=evaluation.toSummaryString("\n结果\n\n",false);
                this.correlationCoefficient=evaluation.correlationCoefficient();
                this.m5p_toString=m5P.toString().replace("LM num","回归函数（LM）");
                ArrayList<Prediction> arrayList=evaluation.predictions();
                Prediction prediction=null;
                PredicInfo predicInfo=null;
                ArrayList<PredicInfo>  arrayList1=new ArrayList<>();
                for(int i=0;i<arrayList.size();i++){
                    prediction=arrayList.get(i);
                    double actual=prediction.actual();
                    double predicted=prediction.predicted();
                    double error=predicted-actual;
                    predicInfo=new PredicInfo(actual,error,predicted);
                    arrayList1.add(predicInfo);
                }
                this.arrayList=arrayList1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

     private double correlationCoefficient;

     private String summay;

     private String m5p_toString;

     private ArrayList<PredicInfo> arrayList;//包括了差值信息

    public ArrayList<PredicInfo> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<PredicInfo> arrayList) {
        this.arrayList = arrayList;
    }

    public double getCorrelationCoefficient() {
        return correlationCoefficient;
    }

    public void setCorrelationCoefficient(double correlationCoefficient) {
        this.correlationCoefficient = correlationCoefficient;
    }

    public String getSummay() {
        return summay;
    }

    public void setSummay(String summay) {
        this.summay = summay;
    }

    public String getM5p_toString() {
        return m5p_toString;
    }

    public void setM5p_toString(String m5p_toString) {
        this.m5p_toString = m5p_toString;
    }


}
