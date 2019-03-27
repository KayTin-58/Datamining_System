package com.zhang.service.chart;

import com.zhang.service.chart.data.BT_Series;
import com.zhang.service.chart.data.Series;
import weka.core.Instances;

import java.util.ArrayList;

/**
 * Created by 直到世界尽头 on 2018/4/19.
 */
public interface ChartInterface {

    /**
     * 统计信息(标称型数据)
     * @param attributeName
     * @param index
     * @throws Exception
     */
    public ArrayList<Series> getInforMation(String attributeName, Integer index, Instances  instances)throws Exception;


    public ArrayList<BT_Series> getInfor_BT(String attributeName, Integer index, Instances  instances)throws Exception;


    public ArrayList<String> getClass_value(Instances  instances)throws Exception;
}
