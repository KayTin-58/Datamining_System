package com.zhang.entity.classifyResult;

import com.zhang.entity.ResponseInfoEntity;

import java.io.Serializable;

/**
 * Created by 直到世界尽头 on 2018/4/8.
 */
public class J48ResultFinaly extends ResponseInfoEntity implements Serializable{
    private J48Result j48Result;
    private String[] summaryStrings;
    private String[] matrixStrings;
    private String[] classDetailsStrings;
    private String[] classifys;

    public J48ResultFinaly(J48Result j48Result){
        if(j48Result!=null){
            this.j48Result=j48Result;
            summaryStrings=j48Result.getSummaryString().split("\\n");
            matrixStrings=j48Result.getMatrixString().split("\\n");
            classDetailsStrings=j48Result.getClassDetailsString().split("\\n");
            classifys=j48Result.getClassify().split("\\n");
        }

//        for(int i=0;i<summaryStrings.length;i++){
//           System.out.println( summaryStrings[i]);
//        }
//
//        for(int i=0;i<matrixStrings.length;i++){
//            System.out.println( matrixStrings[i]);
//        }
//
//        for(int i=0;i<classDetailsStrings.length;i++){
//            System.out.println( classDetailsStrings[i]);
//        }
//
//        for(int i=0;i<classifys.length;i++){
//            System.out.println( classifys[i]);
//        }
//


    }

    public String[] getSummaryStrings() {
        return summaryStrings;
    }

    public void setSummaryStrings(String[] summaryStrings) {
        this.summaryStrings = summaryStrings;
    }

    public String[] getMatrixStrings() {
        return matrixStrings;
    }

    public void setMatrixStrings(String[] matrixStrings) {
        this.matrixStrings = matrixStrings;
    }

    public String[] getClassDetailsStrings() {
        return classDetailsStrings;
    }

    public void setClassDetailsStrings(String[] classDetailsStrings) {
        this.classDetailsStrings = classDetailsStrings;
    }

    public String[] getClassifys() {
        return classifys;
    }

    public void setClassifys(String[] classifys) {
        this.classifys = classifys;
    }

    public J48Result getJ48Result() {
        return j48Result;
    }

    public void setJ48Result(J48Result j48Result) {
        this.j48Result = j48Result;
    }


    @Override
    public String toString() {
        for(int i=0;i<summaryStrings.length;i++){
            System.out.println( summaryStrings[i]);
        }

        for(int j=0;j<matrixStrings.length;j++){
            System.out.println( matrixStrings[j]);
        }

        for(int m=0;m<classDetailsStrings.length;m++){
            System.out.println( classDetailsStrings[m]);
        }

        for(int n=0;n<classifys.length;n++){
            System.out.println( classifys[n]);
        }
        return "J48ResultFinaly{}";
    }
}
