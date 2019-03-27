package com.zhang.entity.parameter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by 直到世界尽头 on 2018/3/22.
 */
public class Attribute_FilterParams implements Serializable {
    private Double scale;

    private Double translation;

    private Integer[] indexs;


    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public Double getTranslation() {
        return translation;
    }

    public void setTranslation(Double translation) {
        this.translation = translation;
    }

    public Integer[] getIndexs() {
        return indexs;
    }

    public void setIndexs(Integer[] indexs) {
        this.indexs = indexs;
    }

    @Override
    public String toString() {
        return "Attribute_FilterParams{" +
                "scale=" + scale +
                ", translation=" + translation +
                ", indexs=" + Arrays.toString(indexs) +
                '}';
    }
}
