package com.zhang.entity;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/3/16.
 */
public class AttributeAnalyEntity  extends  ResponseInfoEntity implements Serializable{

    private String attribute_Name;

    private String num_type;

    private int missing;

    private String  missing_percentage;

    private double distinct;

    private double unique;

    private double weight;

    public String getAttribute_Name() {
        return attribute_Name;
    }

    public String getNum_type() {
        return num_type;
    }


    public double getDistinct() {
        return distinct;
    }

    public double getUnique() {
        return unique;
    }


    public void setAttribute_Name(String attribute_Name) {
        this.attribute_Name = attribute_Name;
    }

    public void setNum_type(String num_type) {
        this.num_type = num_type;
    }


    public void setDistinct(double distinct) {
        this.distinct = distinct;
    }

    public void setUnique(double unique) {
        this.unique = unique;
    }

    public void setMissing(int missing) {
        this.missing = missing;
    }

    public void setMissing_percentage(String missing_percentage) {
        this.missing_percentage = missing_percentage;
    }

    public int getMissing() {
        return missing;
    }

    public String getMissing_percentage() {
        return missing_percentage;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "AttributeAnalyEntity{" +
                "attribute_Name='" + attribute_Name + '\'' +
                ", num_type='" + num_type + '\'' +
                ", missing=" + missing +
                ", missing_percentage='" + missing_percentage + '\'' +
                ", distinct=" + distinct +
                ", unique=" + unique +
                ", weight=" + weight +
                '}';
    }
}
