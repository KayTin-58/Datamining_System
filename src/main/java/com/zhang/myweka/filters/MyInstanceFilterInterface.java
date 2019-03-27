package com.zhang.myweka.filters;

import weka.core.Instances;

/**
 * 无监督实例过滤器接口
 * Created by 直到世界尽头 on 2018/3/24.
 */
public interface MyInstanceFilterInterface {


    /**
     * 随机化实例重排
     * @param instances
     * @param no
     * @return
     * @throws Exception
     */
    public Instances my_Randomize(Instances instances,double no)throws Exception;


    /**
     * 抽取随机样本
     * @param instances
     * @param sampleSize
     * @return
     * @throws Exception
     */
    public Instances my_ReservoirSample(Instances instances,double sampleSize)throws Exception;


    /**
     * 删除 percentage的数据
     * @param instances
     * @param percentage
     * @return
     * @throws Exception
     */
    public Instances my_RemovePercentage(Instances instances,double percentage)throws  Exception;


    /**
     * 删除重复实例
     * @param instances
     * @param no
     * @return
     */
    public Instances my_Removeduplicates(Instances instances,double no)throws Exception;


    public Instances my_InstanceFilter(Instances instances,double parameter,String filterName)throws  Exception;
}
