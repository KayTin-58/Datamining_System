package com.zhang.dto;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/4/6.
 */
public class EvaluationAndClassNameAndClassModel implements Serializable{
    private Evaluation evaluation;
    private String className;
    private Classifier classifier;

    public EvaluationAndClassNameAndClassModel(Evaluation evaluation, String className, Classifier classifier) {
        this.evaluation = evaluation;
        this.className = className;
        this.classifier=classifier;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }
}
