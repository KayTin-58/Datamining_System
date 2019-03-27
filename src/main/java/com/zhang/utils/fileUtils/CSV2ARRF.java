package com.zhang.utils.fileUtils;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 直到世界尽头 on 2018/3/8.
 */
public class CSV2ARRF {


    CSVLoader csvLoader=new CSVLoader();


    /**
     *将Csv文件转换成Arrf文件并保存
     * @param csvFileName   Csv文件地址
     * @param arffFilePath  Arrf文件保存地址
     * @return
     */
    public  Boolean  Csv2Arrf(String  csvFileName,String arffFilePath){
        try {
            csvLoader.setSource(new File(csvFileName));
            Instances datasrc = csvLoader.getDataSet();

            ArffSaver saver = new ArffSaver();
            saver.setInstances(datasrc);
            saver.setFile(new File(arffFilePath));
            saver.writeBatch();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     *
     * @param inputStream
     * @param arffFilePath
     * @return
     */
    public  Boolean  Csv2Arrf(InputStream inputStream, String arffFilePath){
        try {
            csvLoader.setSource(inputStream);
            Instances datasrc = csvLoader.getDataSet();

            ArffSaver saver = new ArffSaver();
            saver.setInstances(datasrc);
            saver.setFile(new File(arffFilePath));
            saver.writeBatch();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
