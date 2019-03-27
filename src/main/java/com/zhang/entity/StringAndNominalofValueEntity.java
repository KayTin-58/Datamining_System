package com.zhang.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 *     Name  	    Counter  	    Weight
 0  	    hot  	    4  	    4
 1  	    mild  	    5  	    5
 2  	    cool  	    4  	    4
 * Created by 直到世界尽头 on 2018/3/16.
 */
public class StringAndNominalofValueEntity implements Serializable {

    private String lable;
    private int count;
    private String weight;

    private ArrayList<String> statistics_value_list;

    public String getLable() {
        return lable;
    }

    public int getCount() {
        return count;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public ArrayList<String> getStatistics_value_list() {
        return statistics_value_list;
    }

    public void setStatistics_value_list(ArrayList<String> statistics_value_list) {
        this.statistics_value_list = statistics_value_list;
    }
}
