package com.zhang.service.chart.data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by 直到世界尽头 on 2018/4/19.
 */
public class Series implements Serializable{


    private String name;

    private int[] data;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int r=17;
        r+=this.name.hashCode();
        return r;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof Series))
            return false;
        Series series = (Series) obj;
        return series.getName().equals(this.getName());
    }


    @Override
    public String toString() {
        return name+"--"+ Arrays.toString(this.data);
    }
}
