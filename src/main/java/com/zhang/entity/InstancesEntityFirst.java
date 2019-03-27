package com.zhang.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 上传文件后获得第一个页面对象
 * Created by 直到世界尽头 on 2018/3/16.
 */
public class InstancesEntityFirst extends ResponseInfoEntity implements Serializable{

    private String file_name;
    private int attributes_size;
    private int instance_size;

    private ArrayList<AttributeNameAndType> attribute_list;//属性对应的下标和对应的属性名称


    public String getFile_name() {
        return file_name;
    }

    public int getAttributes_size() {
        return attributes_size;
    }

    public int getInstance_size() {
        return instance_size;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setAttributes_size(int attributes_size) {
        this.attributes_size = attributes_size;
    }

    public void setInstance_size(int instance_size) {
        this.instance_size = instance_size;
    }

    public ArrayList<AttributeNameAndType> getAttribute_list() {
        return attribute_list;
    }

    public void setAttribute_list(ArrayList<AttributeNameAndType> attribute_list) {
        this.attribute_list = attribute_list;
    }

    @Override
    public String toString() {
        return "InstancesEntityFirst{" +
                "file_name='" + file_name + '\'' +
                ", attributes_size=" + attributes_size +
                ", instance_size=" + instance_size +
                ", attribute_list=" + attribute_list +
                '}';
    }
}
