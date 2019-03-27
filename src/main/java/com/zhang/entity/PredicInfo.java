package com.zhang.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by 直到世界尽头 on 2018/4/17.
 */
public class PredicInfo implements Serializable{
    private String actual;
    private String error;
    private String predicted;



    public PredicInfo(){}

    public PredicInfo(double actual, double error, double predicted) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        this.actual = df.format(actual);
        this.error = df.format(error);
        this.predicted =df.format(predicted);
        df=null;//GC
    }


    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPredicted() {
        return predicted;
    }

    public void setPredicted(String predicted) {
        this.predicted = predicted;
    }


    @Override
    public String toString() {
        return "PredicInfo{" +
                "actual='" + actual + '\'' +
                ", error='" + error + '\'' +
                ", predicted='" + predicted + '\'' +
                '}';
    }
}
