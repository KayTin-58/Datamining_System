package com.zhang.entity.classifyResult;


import com.zhang.dto.EvaluationAndClassNameAndClassModel;
import com.zhang.entity.ResponseInfoEntity;
import weka.classifiers.evaluation.Evaluation;

import java.io.Serializable;

/**
 * J48分类器分类结果
 * Created by 直到世界尽头 on 2018/4/6.
 */
public class J48Result extends ResponseInfoEntity implements Serializable{


    public J48Result(){

    }
    public J48Result(EvaluationAndClassNameAndClassModel evaluationAndClassName)throws Exception{

        this.className=evaluationAndClassName.getClassName();
        Evaluation evaluation=evaluationAndClassName.getEvaluation();
        this.correctNum=evaluation.correct();
        this.pctCorrect=evaluation.pctCorrect();
        this.incorrectNum=evaluation.incorrect();
        this.pctIncorrect=evaluation.pctIncorrect();
        this.kappa=evaluation.kappa();
        this.mean_abs_error=evaluation.meanAbsoluteError();
        this.root_mean_error=evaluation.rootMeanSquaredError();
        try {
            this.relative_abs_error=evaluation.relativeAbsoluteError();
        } catch (Exception e) {
            e.printStackTrace();
            this.relative_abs_error=-9999;
        }
       this.root_relative_squ_error=evaluation.rootRelativeSquaredError();
       this.cov_of_case=evaluation.coverageOfTestCasesByPredictedRegions();
        this.mean_rel_size=evaluation.sizeOfPredictedRegions();
        this.total=evaluation.totalCost();

        this.summaryString=evaluation.toSummaryString("\n结果\n\n",false);
        this.matrixString=evaluation.toMatrixString().replace("Confusion Matrix","结果混淆矩阵");
        this.classDetailsString=evaluation.toClassDetailsString();
        this.classify=evaluationAndClassName.getClassifier().toString();
    }


//     System.out.print(evaluation.toSummaryString("\n结果\n\n",false));
//        System.out.println();
//        System.out.println("输出混淆矩阵：");
//        System.out.println(evaluation.toMatrixString());
//        System.out.println("evaluation.toClassDetailsString()");
//        System.out.println(evaluation.toClassDetailsString());
//        System.out.println("输出生成的模型");
//        j48.buildClassifier(instances);
//        System.out.println(j48);
//


    private String summaryString;
    private String matrixString;
    private String classDetailsString;
    private String classify;

    private String className;

    //正确分类的实例数
    private double correctNum;

    //正确分类的比率
    private double pctCorrect;

    //错误分类的实例数
    private double incorrectNum;

    //错误分类的比率
    private double pctIncorrect;

    //kappa统计值
    private double kappa;

    //平均绝对误差
    private double mean_abs_error;

    //均方根误差
    private double root_mean_error;

    //相对绝对误差
    private double relative_abs_error;

    //相对均方误差
    private double root_relative_squ_error;

    //案例覆盖率
    private double cov_of_case;

    //平均相对区域大小
    private double mean_rel_size;

    //实例总数
    private double total;


    public String getSummaryString() {
        return summaryString;
    }

    public void setSummaryString(String summaryString) {
        this.summaryString = summaryString;
    }

    public String getMatrixString() {
        return matrixString;
    }

    public void setMatrixString(String matrixString) {
        this.matrixString = matrixString;
    }

    public String getClassDetailsString() {
        return classDetailsString;
    }

    public void setClassDetailsString(String classDetailsString) {
        this.classDetailsString = classDetailsString;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(double correctNum) {
        this.correctNum = correctNum;
    }

    public double getPctCorrect() {
        return pctCorrect;
    }

    public void setPctCorrect(double pctCorrect) {
        this.pctCorrect = pctCorrect;
    }

    public double getIncorrectNum() {
        return incorrectNum;
    }

    public void setIncorrectNum(double incorrectNum) {
        this.incorrectNum = incorrectNum;
    }

    public double getPctIncorrect() {
        return pctIncorrect;
    }

    public void setPctIncorrect(double pctIncorrect) {
        this.pctIncorrect = pctIncorrect;
    }

    public double getKappa() {
        return kappa;
    }

    public void setKappa(double kappa) {
        this.kappa = kappa;
    }

    public double getMean_abs_error() {
        return mean_abs_error;
    }

    public void setMean_abs_error(double mean_abs_error) {
        this.mean_abs_error = mean_abs_error;
    }

    public double getRoot_mean_error() {
        return root_mean_error;
    }

    public void setRoot_mean_error(double root_mean_error) {
        this.root_mean_error = root_mean_error;
    }

    public double getRelative_abs_error() {
        return relative_abs_error;
    }

    public void setRelative_abs_error(double relative_abs_error) {
        this.relative_abs_error = relative_abs_error;
    }

    public double getRoot_relative_squ_error() {
        return root_relative_squ_error;
    }

    public void setRoot_relative_squ_error(double root_relative_squ_error) {
        this.root_relative_squ_error = root_relative_squ_error;
    }

    public double getCov_of_case() {
        return cov_of_case;
    }

    public void setCov_of_case(double cov_of_case) {
        this.cov_of_case = cov_of_case;
    }

    public double getMean_rel_size() {
        return mean_rel_size;
    }

    public void setMean_rel_size(double mean_rel_size) {
        this.mean_rel_size = mean_rel_size;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "J48Result{" +
                "className='" + className + '\'' +
                ", correctNum=" + correctNum +
                ", pctCorrect=" + pctCorrect +
                ", incorrectNum=" + incorrectNum +
                ", pctIncorrect=" + pctIncorrect +
                ", kappa=" + kappa +
                ", mean_abs_error=" + mean_abs_error +
                ", root_mean_error=" + root_mean_error +
                ", relative_abs_error=" + relative_abs_error +
                ", root_relative_squ_error=" + root_relative_squ_error +
                ", cov_of_case=" + cov_of_case +
                ", mean_rel_size=" + mean_rel_size +
                ", total=" + total +
                '}';
    }
}
