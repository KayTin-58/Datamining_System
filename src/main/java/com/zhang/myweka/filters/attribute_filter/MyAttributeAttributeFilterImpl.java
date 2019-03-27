package com.zhang.myweka.filters.attribute_filter;

import com.zhang.myweka.filters.MyAttributeFilterInterface;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;

/**
 * Created by 直到世界尽头 on 2018/3/22.
 */

@Service
public class MyAttributeAttributeFilterImpl implements MyAttributeFilterInterface {

    private static Logger logger=Logger.getLogger(MyAttributeAttributeFilterImpl.class);

    /**
     * 标称属性转二元属性
     * @param instances
     * @return
     */
    @Override
    public Instances my_NominalToBinary(Instances instances)throws Exception {

        NominalToBinary nominalToBinary=new NominalToBinary();
        nominalToBinary.setOptions(new String[]{"-R","first-last"});
        System.out.println( "参数："+nominalToBinary.getOptions().toString());
        nominalToBinary.setInputFormat(instances);

        return Filter.useFilter(instances,nominalToBinary);
    }


    /**
     * [0,1]
     * @param instances
     * @return
     */
    @Override
    public Instances my_Normalize(Instances instances,double scale,double translation)throws Exception {


        //参数
        double scale_01=1D,translation_01=0d;

        scale_01=(scale==0 ?scale_01:scale);
        translation_01=(translation==0 ? translation_01:translation);

        //第一步：加载数据
//        Instances data=instances;
        //第二步：初始化过滤器
        Normalize normalize=new Normalize();
        //第三步：设置过滤器参数
        normalize.setScale(scale_01);//这个参数时可以设置的
        normalize.setTranslation(translation_01);
        //第四步：g关联数据
        normalize.setInputFormat(instances);
        //第四步：使用过滤器
        return Filter.useFilter(instances,normalize);
    }


    /**
     *
     * @param instances
     * @param indexs 这里的下标从 1 开始
     * @return
     */
    @Override
    public Instances my_StringToNominal(Instances instances,int... indexs) throws  Exception{
        String[] options=new String[2];
        options[0]="-R";
        StringToNominal stringToNominal=new StringToNominal();
        //第三步：设置过滤器参数
        if(indexs!=null&& indexs.length>0){
//            for(int i=0;i<indexs.length;i++){
//                options[i]=Integer.toString(indexs[i]);
//            }
        }else{
            options[1]="first-last";
        }
        stringToNominal.setOptions(options);

        stringToNominal.setInputFormat(instances);

        return Filter.useFilter(instances,stringToNominal);
    }


    @Override
    public Instances my_ReplaceMissingValue(Instances instances) throws Exception {

        ReplaceMissingValues replaceMissingValues=new ReplaceMissingValues();
        replaceMissingValues.setInputFormat(instances);
        return Filter.useFilter(instances,replaceMissingValues);
    }

    @Override
    public Instances my_Discretize(Instances instances)throws  Exception{
        Discretize discretize=new Discretize();
        discretize.setMakeBinary(true);
        discretize.setOptions(new String[]{"-R","first-last"});
        discretize.setInputFormat(instances);
        Instances instances1=Filter.useFilter(instances,discretize);
        return Filter.useFilter(instances,discretize);
    }


    @Override
    public Instances my_Num2nom(Instances instances)throws Exception {
        NumericToNominal numericToNominal=new NumericToNominal();
        //numericToNominal.setOptions(new String[]{"-R","first-last"});
        numericToNominal.setInputFormat(instances);
        return Filter.useFilter(instances,numericToNominal);
    }

    @Override
    public Instances my_Filter(Instances instances, String filter_name,double scale,double translation,int... indexs)throws Exception {
        System.out.println("调用过滤器："+filter_name);
        Instances instances1=null;
        switch (filter_name){
            case "normalize":
                instances1=this.my_Normalize(instances,scale,translation);
                break;
            case "nom2bin":
                instances1=this.my_NominalToBinary(instances);
                break;
            case "string2nom":
                instances1=this.my_StringToNominal(instances,indexs);
                break;
            case"replaceMissingvalue":
                instances1=this.my_ReplaceMissingValue(instances);
                break;
            case "discretize":
                instances1=this.my_Discretize(instances);
                break;
            case "num2nom":
                instances1=this.my_Num2nom(instances);
                return instances1;
        }
        return instances1;
    }
}
