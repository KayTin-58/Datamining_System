package com.zhang.dto;

import com.zhang.entity.AttributeAnalyEntity;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/3/17.
 */
public class NumericOfValueEntity extends AttributeAnalyEntity implements Serializable {

    public NumericOfValueEntity(AttributeAnalyEntity attributeAnalyEntity){
        this.setWeight(attributeAnalyEntity.getWeight());
        this.setDistinct(attributeAnalyEntity.getDistinct());
        this.setUnique(attributeAnalyEntity.getUnique());
        this.setMissing(attributeAnalyEntity.getMissing());
        this.setAttribute_Name(attributeAnalyEntity.getAttribute_Name());
        this.setMissing_percentage(attributeAnalyEntity.getMissing_percentage());
        this.setNum_type(attributeAnalyEntity.getNum_type());
    }

    private String maxValue;

    private String minValue;

    private String mean;

    private String stdDev;

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getStdDev() {
        return stdDev;
    }

    public void setStdDev(String stdDev) {
        this.stdDev = stdDev;
    }

    @Override
    public String toString() {
        return "NumericOfValueEntity{" +
                "maxValue='" + maxValue + '\'' +
                ", minValue='" + minValue + '\'' +
                ", mean='" + mean + '\'' +
                ", stdDev='" + stdDev + '\'' +
                '}';
    }
}
