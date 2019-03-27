package com.zhang.myweka.filters;

import weka.core.Instances;

/**
 * 无监督属性过滤接口
 * Created by 直到世界尽头 on 2018/3/22.
 */
public interface MyAttributeFilterInterface {


    /**
     *
     * @param instances
     * @param scale        活动值
     * @param translation  起始值
     * @return
     * @throws Exception
     */
    public Instances my_Normalize(Instances instances,double scale,double translation) throws Exception ;


    /**
     * 多元属性转二元属性
     * @param instances
     * @return
     */
    public Instances my_NominalToBinary(Instances instances)throws  Exception;


    /**
     *
     * @param instances
     * @param indexs   这里的下标需要从 1 开始
     * @return
     * @throws Exception
     */
    public Instances my_StringToNominal(Instances instances,int... indexs)throws Exception;


    /**
     * 填补空缺值
     * @param instances
     * @return
     * @throws Exception
     */
    public Instances my_ReplaceMissingValue(Instances instances)throws Exception;


    /**
     * 二元化的无监督离散化
     * @param instances
     * @return
     */
    public Instances my_Discretize(Instances instances)throws Exception;


    /**
     * 数值型转标称型
     * @param instances
     * @return
     * @throws Exception
     */
    public Instances my_Num2nom(Instances instances)throws Exception;



    public Instances my_Filter(Instances instances, String filter_name,double scale,double translation,int... indexs)throws Exception;
}
