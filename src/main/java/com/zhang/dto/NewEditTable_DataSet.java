package com.zhang.dto;

import com.zhang.entity.EditTable_DataSet;
import com.zhang.entity.ResponseInfoEntity;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/4/11.
 */
public class NewEditTable_DataSet extends ResponseInfoEntity implements Serializable{
    private int totalPage;
    private EditTable_DataSet editTable_dataSet;

    public NewEditTable_DataSet(EditTable_DataSet editTable_dataSet){
        this.editTable_dataSet=editTable_dataSet;
    }

    public EditTable_DataSet getEditTable_dataSet() {
        return editTable_dataSet;
    }

    public void setEditTable_dataSet(EditTable_DataSet editTable_dataSet) {
        this.editTable_dataSet = editTable_dataSet;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
