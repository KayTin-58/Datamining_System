package com.zhang.entity;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/4/21.
 */
public class IKEntity implements Serializable,Comparable<IKEntity>{
    private String key;
    private Integer count;

    public IKEntity(String key, Integer count) {
        this.key = key;
        this.count = count;
    }

    public IKEntity() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    @Override
    public int compareTo(IKEntity o) {
        return -(this.getCount()-o.getCount());
    }
}

