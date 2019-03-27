package com.zhang.service.chart.data;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/4/19.
 */
public class BT_Series implements Serializable {

    private String name;

    private double y;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
