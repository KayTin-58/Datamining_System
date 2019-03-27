package com.zhang.service.impl;

import com.zhang.entity.AttributeNameAndType;
import com.zhang.entity.EditTable_DataSet;
import com.zhang.entity.FileEntity;
import com.zhang.entity.constantEntity.ConstantEntity;
import com.zhang.myweka.loader.MyArffLoader;
import com.zhang.myweka.loader.MyCsvLoader;
import com.zhang.pojo.SaveFileResult;
import com.zhang.service.FileHelpService;
import com.zhang.utils.fileUtils.FileUtils;
import com.zhang.utils.fileUtils.XLS2CSV;
import com.zhang.utils.fileUtils.XLSX2CSV;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 直到世界尽头 on 2018/3/15.
 */
@Service
public class FileHelpServiceImpl implements FileHelpService{



    @Override
    public SaveFileResult saveFile(MultipartFile file,String genType,String ip) {

        String path=ConstantEntity.File_Save_Path;

        if(genType!=null&"Classify".equals(genType)){
            path=ConstantEntity.Classify_File_Path;
        }else if(genType!=null&"Pretreatment".equals(genType)){
            path=ConstantEntity.File_Save_Path;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String time=df.format(new Date()).toString();

        FileEntity fileEntity=new FileEntity(file);
        String fileType=fileEntity.getFile_type();
        String newIp=ip.replace(':','.');
        String file_path=path+"/"+newIp+"_"+fileEntity.getOriginalFilename();




        if("arff".equals(fileType)||"csv".equals(fileType)){
            //直接进行保存
            try {
                System.out.print(file_path);
                File file1=new File(file_path);
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
                return new SaveFileResult(e.getMessage().toString(),false);
            }
        } else if("xls".equals(fileType)){
            //将xls文件转换成csv文件进行保存
            String fileName=fileEntity.getFileNmae();
            System.out.println("文件名："+fileName);
            file_path=path+"/"+time+"_"+fileName+".csv";
            try {
                XLS2CSV xls2CSV=new XLS2CSV(file.getInputStream(),file_path);
                xls2CSV.process();
            } catch (Exception e) {
                e.printStackTrace();
                return new SaveFileResult(e.getMessage().toString(),false);
            }


        }else if("xlsx".equals(fileType)){
            //将xlsx文件转换成csv文件进行保存

            String fileName=fileEntity.getFileNmae();
            System.out.println("文件名："+fileName);
            file_path=path+"/"+time+"_"+fileName+".csv";
            try {
                XLSX2CSV xlsx2CSV=new XLSX2CSV(file.getInputStream(),file_path);
                xlsx2CSV.process();
            } catch (Exception e) {
                e.printStackTrace();
                return new SaveFileResult(e.getMessage().toString(),false);
            }
        }

         return new SaveFileResult(file_path,true);
    }



    @Override
    public Instances file2InstancesByFile(MultipartFile file,SaveFileResult saveFileResult) {
        //首先判断是什么类型的文件
        FileEntity fileEntity=new FileEntity(file);
        String file_type=fileEntity.getFile_type();
        Instances instances=null;
        if(!saveFileResult.getFlag()){
            return instances;
        }
        if("csv".equals(file_type)){
            MyCsvLoader myCsvLoader=new MyCsvLoader();
            try {
                myCsvLoader.setSource(new File(saveFileResult.getFile_path()));
                instances=myCsvLoader.getDataSet();
            } catch (IOException e) {
                e.printStackTrace();
                return instances;
            }

        }else if("arff".equals(file_type)){
            MyArffLoader myArffLoader=new MyArffLoader();
            try {
                myArffLoader.setSource(new File(saveFileResult.getFile_path()));
                instances=myArffLoader.getDataSet();
            } catch (IOException e) {
                e.printStackTrace();
                return instances;
            }

        }else if("xls".equals(file_type)||"xlsx".equals(file_type)){
            //以csv形式读取
            MyCsvLoader myCsvLoader= null;
            try {
                myCsvLoader = new MyCsvLoader();
                myCsvLoader.setSource(new File(saveFileResult.getFile_path()));
                instances=myCsvLoader.getDataSet();
            } catch (Exception e) {
                e.printStackTrace();
                return instances;
            }
        }
        return instances;
    }

    @Override
    public Instances file2InstancesByIO(MultipartFile file,SaveFileResult saveFileResult) {
        FileEntity fileEntity=new FileEntity(file);
        String file_type=fileEntity.getFile_type();
        Instances instances=null;
        if(file.isEmpty()){
            return instances;
        }

        if("csv".equals(file_type)){
            MyCsvLoader myCsvLoader=new MyCsvLoader();
            try {
                myCsvLoader.setSource(file.getInputStream());
                instances=myCsvLoader.getDataSet();
            } catch (IOException e) {
                e.printStackTrace();
                return instances;
            }

        }else if("arff".equals(file_type)){
            MyArffLoader myArffLoader=new MyArffLoader();
            try {
                myArffLoader.setSource(file.getInputStream());
                instances=myArffLoader.getDataSet();
            } catch (IOException e) {
                e.printStackTrace();
                return instances;
            }
        }else if("xls".equals(file_type)||"xlsx".equals(file_type)){
            //以csv形式读取
            MyCsvLoader myCsvLoader= null;
            try {
                myCsvLoader = new MyCsvLoader();
                myCsvLoader.setSource(new File(saveFileResult.getFile_path()));
                instances=myCsvLoader.getDataSet();
            } catch (Exception e) {
                e.printStackTrace();
                return instances;
            }
        }
        return instances;
    }


    @Override
    public Instances remove_attribute(int[] attribute_list,Instances instances) {

        Instances instances1=null;
        Remove remove=new Remove();

        remove.setAttributeIndicesArray(attribute_list);
        try {
            remove.setInputFormat(instances);
            instances1= Filter.useFilter(instances,remove);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances1;
    }


    /**
     * 需要把每一行数据的缺失率算出来
     *
     * @param instances
     * @return
     */
    @Override
    public EditTable_DataSet instance2tableDataSet(Instances instances) {

        EditTable_DataSet editTable_dataSet=new EditTable_DataSet();


        //第一步：封装属性集合
        ArrayList<AttributeNameAndType> attributeNameAndType_list=new ArrayList<>();
        AttributeNameAndType attributeNameAndType1=null;
        Attribute attribute=null;

        Enumeration<Attribute> enumeration_attribute= instances.enumerateAttributes();//属性集合

        while(enumeration_attribute.hasMoreElements()){
            attribute=enumeration_attribute.nextElement();
            String attribute_name= attribute.name();
            attributeNameAndType1=new AttributeNameAndType(attribute_name,attribute.type());
            attributeNameAndType_list.add(attributeNameAndType1);
        }
        editTable_dataSet.setAttributeName_list(attributeNameAndType_list);


        //第二步：封装实例数据集
        Enumeration<Instance> enumeration_instance=instances.enumerateInstances();//数据实例集合

        //一个实例数据
        ArrayList<String> one_instance=null;
        ArrayList<ArrayList<String>> arrayLists=new ArrayList<>();
        int index=0;

        //遍历数据集合
        while(enumeration_instance.hasMoreElements()){

            //
            Instance instance=(Instance) enumeration_instance.nextElement();

            String instance_str=instance.toString();
            String[] strs=instance_str.split(",");

            int null_length = ("," + instance_str + ",").split("\\?").length-1;
            double all_lenth=strs.length;
            String percentage= Utils.doubleToString(null_length/all_lenth,3);
            //System.out.println("占比："+percentage);
            one_instance=new ArrayList<>();
            one_instance.add(percentage);
            one_instance.addAll(Arrays.asList(strs));
            arrayLists.add(one_instance);
        }

        editTable_dataSet.setAttributeValue_list(arrayLists);
        return editTable_dataSet;
    }


    @Override
    public InputStream file_saveAsARRF(Instances instances, String file_name)throws Exception{

        ArffSaver arffSaver=new ArffSaver();
        File file=new File(ConstantEntity.Save_File_Path+"/"+file_name);
        arffSaver.setInstances(instances);
        try {
            arffSaver.setFile(file);
            arffSaver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new FileInputStream(file);
/*
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(1024);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(instances);
        InputStream  byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;*/

    }


    /**
     * 保存分作两步：
     *     第一步：将目标一csv文件形式保存在本地
     *     第二步：实现从本地的下载
     *     注意：这是一个事物 要保证同步
     * @param instances
     * @param file_name
     * @return
     */
    @Override
    public InputStream file_saveAsCSV(Instances instances, String file_name)throws Exception{


        CSVSaver csvSaver=new CSVSaver();
        File file=new File(ConstantEntity.Save_File_Path+"/"+file_name);

        csvSaver.setInstances(instances);
        try {
            csvSaver.setFile(file);
            csvSaver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("asfhdjkfj;dlfk;'"+file.exists());
            return null;
        }
        return new FileInputStream(file);
    }

    @Override
    public InputStream file_save(Instances instances, String file_name,String ip)throws Exception {
        String file_type=FileUtils.getFileType(file_name);
        String str= UUID.randomUUID().toString()+"_"+ip.replace(':','-');
        String fileName_new=str+file_name;
        InputStream fileInputStream=null;

        if("arff".equals(file_type)){
            fileInputStream=this.file_saveAsARRF(instances,fileName_new);
        }else if("csv".equals(file_type)){
            fileInputStream=this.file_saveAsCSV(instances,fileName_new);
        }else if("xlsx".equals(file_type)||"xls".equals(file_type)){
            String file_name_01= FileUtils.getFileName(file_name);
            file_name_01=str+file_name_01+".csv";
            fileInputStream=this.file_saveAsCSV(instances,file_name_01);
        }else {
            return null;
        }
        return fileInputStream;
    }

    public String getAttributeOfType(Attribute  attribute){
        String type=null;
        int type_int=attribute.type();
     switch (type_int){
         case 1:
             type="Nominal";
             break;
         case 0:
         case 3:
              type="Numeric";
              break;
         case 4:
              type="RelationValued";
              break;
         case 2:
             type="String";
             break;
        default:
             type="Unknow";
             break;
     }

        return type;
    }

}
