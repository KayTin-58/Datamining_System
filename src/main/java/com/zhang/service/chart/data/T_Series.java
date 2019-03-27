package com.zhang.service.chart.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 直到世界尽头 on 2018/4/19.
 */
public class T_Series implements Serializable {

    private ArrayList<Series>  seriesArrayList;

    private ArrayList<BT_Series> bt_seriesArrayList;

    private ArrayList<String> categories;


    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<Series> getSeriesArrayList() {
        return seriesArrayList;
    }

    public void setSeriesArrayList(ArrayList<Series> seriesArrayList) {
        this.seriesArrayList = seriesArrayList;
    }

    public ArrayList<BT_Series> getBt_seriesArrayList() {
        return bt_seriesArrayList;
    }

    public void setBt_seriesArrayList(ArrayList<BT_Series> bt_seriesArrayList) {
        this.bt_seriesArrayList = bt_seriesArrayList;
    }
}
