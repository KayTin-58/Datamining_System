package com.zhang.controller;

import com.zhang.dto.NewEditTable_DataSet;
import com.zhang.dto.NumericOfValueEntity;
import com.zhang.dto.StrAndNominalOfValueListEntity;
import com.zhang.entity.EditTable_DataSet;
import com.zhang.entity.InstancesEntityFirst;
import com.zhang.entity.MyInstances;
import com.zhang.entity.ResponseInfoEntity;
import com.zhang.myweka.attribute_op.AttributeInterface;
import com.zhang.myweka.filters.MyAttributeFilterInterface;
import com.zhang.myweka.filters.MyInstanceFilterInterface;
import com.zhang.myweka.instances_op.Instances2Entity;
import com.zhang.pojo.SaveFileResult;
import com.zhang.redis.dao.InstancesDao;
import com.zhang.service.FileHelpService;
import com.zhang.service.chart.ChartInterface;
import com.zhang.service.chart.data.BT_Series;
import com.zhang.service.chart.data.Series;
import com.zhang.service.chart.data.T_Series;
import com.zhang.service.pageService.TablePageService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weka.core.Instances;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by 直到世界尽头 on 2018/3/13.
 */


@RestController
@RequestMapping(value = "/fileController")
@Scope("session")
public class FileUploadController extends BaseController{
    private static Logger logger=Logger.getLogger(FileUploadController.class);
    protected Instances temporary_instances;//临时数据对象
    @Autowired
    FileHelpService fileHelpService;
    @Autowired
    Instances2Entity instances2Entity;
    @Autowired
    AttributeInterface attributeInterface;
    @Autowired
    MyAttributeFilterInterface myAttributeFilterInterface;
    @Autowired
    MyInstanceFilterInterface myInstanceFilterInterface;
    @Autowired
    TablePageService tablePageService;
    @Autowired
    ChartInterface chartInterface;
    @Autowired
    InstancesDao instancesDao;



//,method = {RequestMethod.GET}

    /**
     * 文件上传接口
     * @param file
     * @return
     */
    @RequestMapping(value="/fileupload",method = {RequestMethod.POST})
    @ResponseBody
    public InstancesEntityFirst fileUpload(MultipartFile file){
        uniqueID= UUID.randomUUID().toString();
        InstancesEntityFirst instancesEntityFirst=new InstancesEntityFirst();
        if(file.isEmpty()){
            //为空文件,
             instancesEntityFirst.setState(400);
             instancesEntityFirst.setError_messaege("上传的文件为空文件!");
        }

        fileName=file.getOriginalFilename();
        //第一步：判断文件类型并进行转换保存
        SaveFileResult saveFileResult=fileHelpService.saveFile(file,"Pretreatment",ip);

        if(!saveFileResult.getFlag()){
            //保存不成功
            instancesEntityFirst.setState(400);
            instancesEntityFirst.setError_messaege("文件转存出现错误!");
            return  instancesEntityFirst;
        }
        //第二步：得到Instances对象,设置类别属性（默认最后一列为类别属性）

        Instances instances=fileHelpService.file2InstancesByFile(file,saveFileResult);
        //myInstances=new MyInstances(fileHelpService.file2InstancesByFile(file,saveFileResult));

        /**
         * 新增的实验步骤
         */
        //instances.setClassIndex(instances.numAttributes()-1);
        if(instances==null){
            //保存不成功
            instancesEntityFirst.setState(400);
            instancesEntityFirst.setError_messaege("文件解析出现异常!");
            return  instancesEntityFirst;
        }
        instancesDao.save(instances,uniqueID);
        //第三步：解析Instances对象，返回结果
        instancesEntityFirst=instances2Entity.instances2EntityForst(instances,fileName);
        return  instancesEntityFirst;
    }

    /**
     * 页面初识加载时初始化数据
     * @return
     */
   @RequestMapping(value="/initData")
   @ResponseBody
    public InstancesEntityFirst initData(){
        InstancesEntityFirst instancesEntityFirst=null;
        Instances instances=instancesDao.getInstances(uniqueID);
       if(instances==null){
           return instancesEntityFirst;
       }else{
           attributeInterface.statistics_valueAndClass(instances,null,0);
           instancesEntityFirst=instances2Entity.instances2EntityForst(instances,fileName);
       }
        return instancesEntityFirst;
    }


    /**
     * Nominal  数据类型分析接口
     * @param attributeName
     * @param index
     * @return
     */
    @RequestMapping(value="/attributeAnalysis_nom0rStr" )
    @ResponseBody
    public StrAndNominalOfValueListEntity attributeAnalysis_nomOrStr(String attributeName, int index){
      //获得属性名称attributeName和属性下标 index
        StrAndNominalOfValueListEntity strAndNominalOfValueListEntity=null;
        Instances instances=instancesDao.getInstances(uniqueID);
        if(instances==null){
            strAndNominalOfValueListEntity=new StrAndNominalOfValueListEntity(null);
            strAndNominalOfValueListEntity.setState(400);
            strAndNominalOfValueListEntity.setError_messaege("内部数据集为空！");
            return strAndNominalOfValueListEntity;
        }

       // NumericOfValueEntity numericOfValueEntity=null;
        if("Nominal".equals(attributeInterface.getAttributeType(instances,attributeName,index))||"String".equals(attributeInterface.getAttributeType(instances,attributeName,index))){
            strAndNominalOfValueListEntity=attributeInterface.getAttributeValuesWhenStrOrNomi(instances ,attributeName,index);
        }else{
            strAndNominalOfValueListEntity=new StrAndNominalOfValueListEntity(null);
            strAndNominalOfValueListEntity.setState(400);
            strAndNominalOfValueListEntity.setError_messaege("未知数据类型！");
            return strAndNominalOfValueListEntity;
        }

        //System.out.println("attributeAnalysis_nom0rStr这个接口被调用啦！！！！！！！！");

        return strAndNominalOfValueListEntity;
    }

    /**
     * Numeric  类型数据分析接口
     * @param attributeName
     * @param index
     * @return
     */
    @RequestMapping(value="/attributeAnalysis_num")
    @ResponseBody
    public NumericOfValueEntity attributeAnalysis_num(String attributeName, int index){
        //获得属性名称attributeName和属性下标 index
         NumericOfValueEntity numericOfValueEntity=null;
       Instances instances=instancesDao.getInstances(uniqueID);
       if("Numeric".equals(attributeInterface.getAttributeType(instances,attributeName,index))||"Date".equals(attributeInterface.getAttributeType(instances,attributeName,index))){
         numericOfValueEntity=attributeInterface.getAttributeValueWhenNumer(instances ,attributeName,index);
        }else{
           numericOfValueEntity.setState(400);
           numericOfValueEntity.setError_messaege("未知数据类型！");
       }

        //System.out.println("attributeAnalysis_num这个接口被调用啦！！！！！！！！"+numericOfValueEntity.toString());
        return numericOfValueEntity;
    }


    /**
     *删除属性接口
     * @param remove_list
     */
    @RequestMapping(value="/attribute_remove" )
    @ResponseBody
    public InstancesEntityFirst remove_attribute(@RequestParam(value="remove_list[]") int[] remove_list,String file_name){
        InstancesEntityFirst instancesEntityFirst=new InstancesEntityFirst();
        if(remove_list==null||remove_list.length<=0){
            instancesEntityFirst.setState(400);
            instancesEntityFirst.setError_messaege("未选中目标！");
            System.out.print("未选中目标！");
        }
        Instances instances=instancesDao.getInstances(uniqueID);

        Instances instances_new=new MyInstances(fileHelpService.remove_attribute(remove_list,instances));
        instancesDao.save(instances_new,uniqueID);
        instancesEntityFirst=instances2Entity.instances2EntityForst(instances_new,file_name);
        //System.out.print("adsaas！"+remove_list.length);
        return instancesEntityFirst;
    }


    /**
     * 编辑数据接口
     */
    @RequestMapping(value = "/edit_dataSet" ,method = {RequestMethod.GET})
    @ResponseBody
    public NewEditTable_DataSet edit_dataSet(int page)throws Exception{

        NewEditTable_DataSet newEditTable_dataSet=new NewEditTable_DataSet(null);
        EditTable_DataSet editTable_dataSet=null;
        Instances instances=instancesDao.getInstances(uniqueID);
        if(instances==null){
            editTable_dataSet= new EditTable_DataSet();
            editTable_dataSet.setState(400);
            editTable_dataSet.setError_messaege("内部数据集为空(可能是还未上传文件)！");
            newEditTable_dataSet.setEditTable_dataSet(editTable_dataSet);
            return  newEditTable_dataSet;
        }
//        editTable_dataSet=fileHelpService.instance2tableDataSet(myInstances);
//        System.out.println("edit_dataSet:属性数量："+editTable_dataSet.getAttributeName_list().size());
//        System.out.println("edit_dataSet:数据实例数量："+editTable_dataSet.getAttributeValue_list().size());
        newEditTable_dataSet=tablePageService.getOnePage(new MyInstances(instances),page);
        return newEditTable_dataSet;
    }


    /**
     * 保存文件
     * @return
     */
    @RequestMapping(value = "/file_save" ,method = {RequestMethod.POST})
    @ResponseBody
    public ResponseInfoEntity file_save(HttpServletResponse response,HttpServletRequest request){

        ResponseInfoEntity responseInfoEntity=new ResponseInfoEntity();

        Instances instances=instancesDao.getInstances(uniqueID);
        //可以选择以csv/或者arrf的形式进行保存，我们选择一次性保存两种格式的文件
        if(fileName==null){
            responseInfoEntity.setState(400);
            responseInfoEntity.setError_messaege("文件名为空！！");
            return responseInfoEntity;
        }
        InputStream fis = null;
        try {
            fis=fileHelpService.file_save(instances,fileName,ip);
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            IOUtils.copyLarge(fis,response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            responseInfoEntity.setState(400);
            responseInfoEntity.setError_messaege("出现异常！！");
            return responseInfoEntity;
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseInfoEntity;
    }


    /**
     *
     * @param filter_name
     * @param
     * @return
     */
    @RequestMapping(value="/attribute_filter",method = {RequestMethod.GET})
    @ResponseBody
    public InstancesEntityFirst attribute_filter(String filter_name ,Double scale,Double translation){

        InstancesEntityFirst instancesEntityFirst=new InstancesEntityFirst();
        System.out.println(filter_name+scale+translation);
        Instances instances=instancesDao.getInstances(uniqueID);

        if(filter_name!=null&&!filter_name.equals("")){
            logger.info("过滤器名称："+filter_name);
            try {
                //数据文件为空
                if(instances==null){
                    //返回
                    logger.error("instances 为空！！！");
                    instancesEntityFirst.setState(400);
                    instancesEntityFirst.setError_messaege("还未上传数据文件！！");
                    return instancesEntityFirst;
                }
                //正确处理
                scale=(scale==null? 0:scale);
                translation=(translation==null? 0:translation);
                Instances instances1_new=new MyInstances(myAttributeFilterInterface.my_Filter(instances,filter_name,scale,translation,null));
                instancesDao.save(instances1_new,uniqueID);
                instancesEntityFirst=instances2Entity.instances2EntityForst(instances1_new,fileName);
                return instancesEntityFirst;
            } catch (Exception e) {

                //出现错误
                e.printStackTrace();
                logger.error(e);
                instancesEntityFirst.setState(400);
                instancesEntityFirst.setError_messaege(e.getMessage());
                //System.out.println(instances);
                return instancesEntityFirst;
            }
        }else{
            //选择器名称为空
            instancesEntityFirst.setState(400);
            instancesEntityFirst.setError_messaege("尚未选择过滤器！！");
            return instancesEntityFirst;
        }
    }

    @RequestMapping(value="/instance_filter")
    @ResponseBody
    public EditTable_DataSet instance_filter(String filter_name,double parameter){


        System.out.print("filter_name:  "+filter_name);
        Instances instances=instancesDao.getInstances(uniqueID);
        EditTable_DataSet editTable_dataSet=null;
        if(filter_name!=null&&!filter_name.equals("")){
           if(instances==null){
               editTable_dataSet=new EditTable_DataSet();
               editTable_dataSet.setState(400);
               editTable_dataSet.setError_messaege("n内部数据文件为空！");
           }
            try {
                if(filter_name.equals("reservoirSample")){
                    //无放回随机采样，需要特殊处理
                    temporary_instances=myInstanceFilterInterface.my_InstanceFilter(instances,parameter,filter_name);
                    editTable_dataSet=fileHelpService.instance2tableDataSet(temporary_instances);
                    return editTable_dataSet;
                }

                Instances instances1_new=new MyInstances(myInstanceFilterInterface.my_InstanceFilter(instances,parameter,filter_name));
                instancesDao.save(instances1_new,uniqueID);
                editTable_dataSet=fileHelpService.instance2tableDataSet(instances1_new);
            } catch (Exception e) {
                //出现错误
                e.printStackTrace();
                editTable_dataSet.setState(400);
                editTable_dataSet.setError_messaege(e.getMessage().toString());
            }
        }else{
            editTable_dataSet=new EditTable_DataSet();
            editTable_dataSet.setState(400);
            editTable_dataSet.setError_messaege("尚未选择过滤器！");
        }
        return editTable_dataSet;
    }


    /**
     * 保存抽样样本数据
     * @return
     */
    @RequestMapping(value = "/save_sample")
    @ResponseBody
    public ResponseInfoEntity save_sample(HttpServletResponse response){
        ResponseInfoEntity responseInfoEntity=new ResponseInfoEntity();
        if(fileName==null){
            responseInfoEntity.setState(400);
            responseInfoEntity.setError_messaege("文件名为空！！");
            return responseInfoEntity;
        }
        InputStream fis = null;
        try {

            if(temporary_instances==null){
                responseInfoEntity.setState(400);
                responseInfoEntity.setError_messaege("还未曾进行删除数据处理！！");
                return responseInfoEntity;
            }
            fis=fileHelpService.file_save(temporary_instances,fileName,ip);
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            IOUtils.copy(fis,response.getOutputStream());
            response.flushBuffer();
            temporary_instances=null;//GC
        } catch (Exception e) {
            e.printStackTrace();
            responseInfoEntity.setState(400);
            responseInfoEntity.setError_messaege("出现异常！！");
            return responseInfoEntity;
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseInfoEntity;
    }

//    @RequestMapping(value = "/test")
//    public  ArrayList<Series> test(){
//        myInstances.setClassIndex(myInstances.numAttributes()-1);
//        ArrayList<Series> arrayList=null;
//        try {
//            arrayList=chartInterface.getInforMation("temperature",0,myInstances);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("长度："+arrayList.size());
//        return arrayList;
//    }

    /**
     * 获取属性集合
     * @return
     */
    @RequestMapping(value = "/getAttributeList")
    @ResponseBody
    public ArrayList<String> getAttributeList(){
        Instances instances=instancesDao.getInstances(uniqueID);
        return  attributeInterface.getAttributr_List(instances);
    }


    /**
     * 获取详细的统计信息
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getInfo")
    @ResponseBody
    public T_Series getInfo(String name)throws Exception{

        System.out.println("属性："+name);
        Instances instances=instancesDao.getInstances(uniqueID);
        ArrayList<Series> arrayList=chartInterface.getInforMation(name,0,instances);
        ArrayList<BT_Series> bt_series=chartInterface.getInfor_BT(name,0,instances);
        ArrayList<String> arrayList1=chartInterface.getClass_value(instances);
        T_Series t_series=new T_Series();
        t_series.setBt_seriesArrayList(bt_series);
        t_series.setSeriesArrayList(arrayList);
        t_series.setCategories(arrayList1);
        return  t_series;
    }


}
