package com.zhang.entity;

/**
 * Created by 直到世界尽头 on 2018/3/17.
 */
public class AttributeNameAndType {


    private String atttribuy_name;
    private String attribute_type;

    public AttributeNameAndType(String atttribuy_name, int type){
        this.atttribuy_name=atttribuy_name;
       /* if(type==1){
            this.attribute_type="Nominal";
        }else if(type==0||type==3){
            this.attribute_type="Numeric";
        }else if(type==4){
            this.attribute_type="RelationValued";
        }else if(type==2){
            this.attribute_type="String";
        }else if(type==3){
            this.attribute_type="Date";
        }else{
            this.attribute_type="Unknow";
        }*/

        switch (type){
            case 1:
                this.attribute_type="Nominal";
                break;
            case 0:
            case 3:
                this.attribute_type="Numeric";
                break;
            case 4:
                this.attribute_type="RelationValued";
                break;
            case 2:
                this.attribute_type="String";
                break;
            default:
                this.attribute_type="Unknow";
                break;
        }
    }

    public String getAtttribuy_name() {
        return atttribuy_name;
    }

    public void setAtttribuy_name(String atttribuy_name) {
        this.atttribuy_name = atttribuy_name;
    }

    public String getAttribute_type() {
        return attribute_type;
    }

    public void setAttribute_type(String attribute_type) {
        this.attribute_type = attribute_type;
    }

    @Override
    public String toString() {
        return "AttributeNameAndType{" +
                "atttribuy_name='" + atttribuy_name + '\'' +
                ", attribute_type='" + attribute_type + '\'' +
                '}';
    }
}
