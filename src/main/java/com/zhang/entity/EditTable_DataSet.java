package com.zhang.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 直到世界尽头 on 2018/3/18.
 */
public class EditTable_DataSet extends ResponseInfoEntity implements Serializable{

   private ArrayList<AttributeNameAndType>  attributeName_list;
    private ArrayList<ArrayList<String>> attributeValue_list;

    public ArrayList<AttributeNameAndType> getAttributeName_list() {
        return attributeName_list;
    }

    public void setAttributeName_list(ArrayList<AttributeNameAndType> attributeName_list) {
        this.attributeName_list = attributeName_list;
    }

    public ArrayList<ArrayList<String>> getAttributeValue_list() {
        return attributeValue_list;
    }

    public void setAttributeValue_list(ArrayList<ArrayList<String>> attributeValue_list) {
        this.attributeValue_list = attributeValue_list;
    }
}
