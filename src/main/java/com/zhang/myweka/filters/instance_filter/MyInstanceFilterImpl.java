package com.zhang.myweka.filters.instance_filter;

import com.zhang.myweka.filters.MyInstanceFilterInterface;
import org.springframework.stereotype.Service;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemoveDuplicates;
import weka.filters.unsupervised.instance.RemoveRange;
import weka.filters.unsupervised.instance.ReservoirSample;

import java.util.Enumeration;

/**
 * Created by 直到世界尽头 on 2018/3/24.
 */
@Service
public class MyInstanceFilterImpl implements MyInstanceFilterInterface{

    @Override
    public Instances my_Randomize(Instances instances, double no) throws Exception {

        Randomize randomize=new Randomize();
        randomize.setRandomSeed(28);
        randomize.setInputFormat(instances);
        return Filter.useFilter(instances,randomize);
    }

    @Override
    public Instances my_ReservoirSample(Instances instances, double sampleSize) throws Exception {

        int instances_length=instances.size();
        int samplesize_01=0;

        if(sampleSize>=instances_length||sampleSize<1){
            samplesize_01=(int)(instances_length*0.8);
        }

        ReservoirSample reservoirSample=new ReservoirSample();
        reservoirSample.setSampleSize(samplesize_01);
        reservoirSample.setInputFormat(instances);

        return Filter.useFilter(instances,reservoirSample);
    }


    /**
     * 删除缺失率大于 percentage 的数据
     * @param instances
     * @param percentage
     * @return
     * @throws Exception
     */
    @Override
    public Instances my_RemovePercentage(Instances instances, double percentage) throws Exception {


        System.out.println("缺失率为："+percentage);
//        if(percentage>=1||percentage<0){
//            return null;
//        }
//        RemovePercentage removePercentage=new RemovePercentage();
//        removePercentage.setPercentage(percentage*100);
//        removePercentage.setInputFormat(instances);
//
//        return Filter.useFilter(instances,removePercentage);

         RemoveRange  removeRange=new RemoveRange();
         Enumeration<Instance> enumeration_instance=instances.enumerateInstances();//数据实例集合



        int count=0;
        String[] options=new String[2];
        options[0]="-R";
        StringBuilder stringBuilder=new StringBuilder();

         while(enumeration_instance.hasMoreElements()){
             count++;
             Instance instance=enumeration_instance.nextElement();
             String instance_str=instance.toString();
             String[] strs=instance_str.split(",");

             int null_length = ("," + instance_str + ",").split("\\?").length-1;
             double all_lenth=strs.length;
             double p=null_length/all_lenth;
             if(p>percentage){
                 stringBuilder.append(count+",");
             }

         }
        options[1]=stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
        for(int i=0;i<options.length;i++){
            System.out.println("参数："+options[i]);
        }
        removeRange.setOptions(options);
        removeRange.setInputFormat(instances);
        return Filter.useFilter(instances,removeRange);
    }

    @Override
    public Instances my_Removeduplicates(Instances instances, double no)throws Exception{

        RemoveDuplicates removeDuplicates=new RemoveDuplicates();
        removeDuplicates.setInputFormat(instances);
        return Filter.useFilter(instances,removeDuplicates);
    }


    /**
     * 根据filterName调用不同的过滤器
     * @param instances
     * @param parameter
     * @param filterName
     * @return
     * @throws Exception
     */
    @Override
    public Instances my_InstanceFilter(Instances instances, double parameter, String filterName) throws Exception {

        Instances instances1=null;

        switch (filterName){
            case "reservoirSample":
              instances1=this.my_ReservoirSample(instances,parameter);
              break;
            case "randomize":
                instances1=this.my_Randomize(instances,0);
                break;
            case "removePercentage":
                instances1=this.my_RemovePercentage(instances,parameter);
                break;
            case "removeDuplicates":
                instances1=this.my_Removeduplicates(instances,0);
                break;
            default:instances1=null;
        }
        return instances1;
    }
}
