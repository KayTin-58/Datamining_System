package com.zhang.entity;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/3/16.
 */
public class ResponseInfoEntity implements Serializable{
    private int state=200;
    private String error_messaege="成功啦!";

    public int getState() {
        return state;
    }

    public String getError_messaege() {
        return error_messaege;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setError_messaege(String error_messaege) {
        this.error_messaege = error_messaege;
    }



}
