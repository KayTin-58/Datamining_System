package com.zhang.entity.parameter;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/4/6.
 */
public class J48Param implements Serializable {

    private String m_BatchSize;
    private float m_ConfidenceFactor;
    private int m_MinNumObj;
    private int m_NumDecimalPlaces;
    private int m_NumFolds;

    public String getM_BatchSize() {
        return m_BatchSize;
    }

    public void setM_BatchSize(String m_BatchSize) {
        this.m_BatchSize = m_BatchSize;
    }

    public float getM_ConfidenceFactor() {
        return m_ConfidenceFactor;
    }

    public void setM_ConfidenceFactor(float m_ConfidenceFactor) {
        this.m_ConfidenceFactor = m_ConfidenceFactor;
    }

    public int getM_MinNumObj() {
        return m_MinNumObj;
    }

    public void setM_MinNumObj(int m_MinNumObj) {
        this.m_MinNumObj = m_MinNumObj;
    }

    public int getM_NumDecimalPlaces() {
        return m_NumDecimalPlaces;
    }

    public void setM_NumDecimalPlaces(int m_NumDecimalPlaces) {
        this.m_NumDecimalPlaces = m_NumDecimalPlaces;
    }

    public int getM_NumFolds() {
        return m_NumFolds;
    }

    public void setM_NumFolds(int m_NumFolds) {
        this.m_NumFolds = m_NumFolds;
    }


    @Override
    public String toString() {
        return "J48Param{" +
                "m_BatchSize='" + m_BatchSize + '\'' +
                ", m_ConfidenceFactor=" + m_ConfidenceFactor +
                ", m_MinNumObj=" + m_MinNumObj +
                ", m_NumDecimalPlaces=" + m_NumDecimalPlaces +
                ", m_NumFolds=" + m_NumFolds +
                '}';
    }
}
