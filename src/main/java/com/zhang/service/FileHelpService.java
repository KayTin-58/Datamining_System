package com.zhang.service;

import com.zhang.entity.EditTable_DataSet;
import com.zhang.pojo.SaveFileResult;
import org.springframework.web.multipart.MultipartFile;
import weka.core.Instances;

import java.io.InputStream;

/**
 * Created by 直到世界尽头 on 2018/3/15.
 */

public interface FileHelpService {


    /**
     * 保存文件
     * @param file
     * @return
     */
    public SaveFileResult saveFile(MultipartFile file,String genType,String ip);


    /**
     * 将文件转换成WEKA的Instances对象
     * @param file IO流
     * @return
     */
    public Instances file2InstancesByIO(MultipartFile file,SaveFileResult saveFileResult);


    /**
     * 将文件转换成WEKA的Instances对象
     * @param file
     * @return
     */
    public Instances file2InstancesByFile(MultipartFile file,SaveFileResult saveFileResult);


    /**
     *删除某个属性
     * @param attribute_list
     * @param instances
     * @return
     */
    public Instances remove_attribute(int[] attribute_list,Instances instances);


    /**
     * 将instance转换成table数据格式
     * @param instances
     */
    public EditTable_DataSet instance2tableDataSet(Instances  instances);


    /**
     * 以csv格式保存文件
     * @param instances
     * @param file_name
     * @return
     */
    public InputStream file_saveAsCSV(Instances instances, String file_name)throws Exception;


    /**
     * 以arff格式保存
     * @param instances
     * @param file_name
     * @return
     */
    public InputStream file_saveAsARRF(Instances instances, String file_name)throws Exception;


    /**
     * 保存文件
     * @param instances
     * @param file_name
     * @return
     */
    public InputStream file_save(Instances instances,String file_name,String ip)throws Exception;
}
