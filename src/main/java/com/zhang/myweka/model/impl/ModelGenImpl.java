package com.zhang.myweka.model.impl;

import com.zhang.myweka.model.ModelGen;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 直到世界尽头 on 2018/4/12.
 */
@Service
public class ModelGenImpl implements ModelGen {


    /**
     * 保存模型
     * @param classifier
     * @throws Exception
     */
    @Override
    public InputStream SaveModel(Classifier classifier,String modelName) throws Exception {
//        String str= UUID.randomUUID().toString();
//        String path=ConstantEntity.Model_File_Path+"/"+str+modelName;
//        //第一步：
//        SerializationHelper.write(path,classifier);
//        //第二步：将文件传换成IO流
//        File file=new File(path);
//        System.out.println("文件是否存在:"+file.exists());
//        if(file.exists()){
//            return  new FileInputStream(file);
//        }
//        return null;

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(classifier);
        InputStream byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;



    }

    @Override
    public void ReadModel() {

    }
}
