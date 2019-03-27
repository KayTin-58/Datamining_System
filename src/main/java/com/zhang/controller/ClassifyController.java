package com.zhang.controller;

import com.zhang.dto.EvaluationAndClassNameAndClassModel;
import com.zhang.dto.NewEditTable_DataSet;
import com.zhang.entity.EditTable_DataSet;
import com.zhang.entity.MyInstances;
import com.zhang.entity.ResponseInfoEntity;
import com.zhang.entity.classifyResult.J48Result;
import com.zhang.entity.classifyResult.J48ResultFinaly;
import com.zhang.entity.classifyResult.M5PResult;
import com.zhang.entity.parameter.J48Param;
import com.zhang.myweka.classify.MyJ48;
import com.zhang.myweka.classify.MyJ48Prediction;
import com.zhang.myweka.classify.MyM5P;
import com.zhang.myweka.classify.MyM5PPrediction;
import com.zhang.myweka.model.ModelGen;
import com.zhang.pojo.SaveFileResult;
import com.zhang.redis.dao.InstancesDao;
import com.zhang.service.FileHelpService;
import com.zhang.service.pageService.TablePageService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import weka.classifiers.Classifier;
import weka.core.Instances;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 直到世界尽头 on 2018/4/4.
 */
@RestController
@RequestMapping(value = "/classify")
@Scope("session")
public class ClassifyController extends BaseController {

    private static Logger logger=Logger.getLogger(ClassifyController.class);
    private Classifier classifier=null,LoadModel=null;//model
    private Instances ukInstances;//待预测的数据(临时变量 要做GC处理)
    private Instances preInstances;//预测结果(临时变量 要做GC处理)

    @Autowired
    FileUploadController fileUploadController;
    @Autowired
    MyJ48 myJ48;
    @Autowired
    FileHelpService fileHelpService;
    @Autowired
    MyJ48Prediction myJ48Prediction;
    @Autowired
    TablePageService tablePageService;
    @Autowired
    ModelGen modelGen;
    @Autowired
    MyM5P myM5P;
    @Autowired
    MyM5PPrediction myM5PPrediction;
    @Autowired
    InstancesDao instancesDao;


    @PostConstruct
    public void init(){
        this.fileName=fileUploadController.fileName;
        uniqueID=fileUploadController.uniqueID;
    }

    /**
     * 使用十字交叉验证的方法
     * @param folds
     */
    @RequestMapping(value = "/cross")
    @ResponseBody
   public J48Result crossValidation(Integer folds, J48Param j48Param){
        System.out.println("j48Param:"+j48Param.toString()+"----folds"+folds);
        J48Result j48Result=null;
       //第一步：获取数据
        //myInstances=fileUploadController.myInstances;
        Instances instances=instancesDao.getInstances(uniqueID);
        if(instances==null){
            j48Result= new J48Result();
            j48Result.setState(400);
            j48Result.setError_messaege("门内数据集为空（可能还未上传数据集）");
            return j48Result;
        }
        //第er步：
        try {
            EvaluationAndClassNameAndClassModel evaluationAndClassNameAndClassModel=myJ48.verificationByCross(instances,folds,-9,j48Param);
            classifier=evaluationAndClassNameAndClassModel.getClassifier();//存储模型
            j48Result=new J48Result(evaluationAndClassNameAndClassModel);
        } catch (Exception e) {
            e.printStackTrace();
            j48Result= new J48Result();
            j48Result.setState(400);
            j48Result.setError_messaege(e.toString());
            //System.out.print("训练模型错误："+e.toString());
            return j48Result;
        }
        J48ResultFinaly j48ResultFinaly=new J48ResultFinaly(j48Result);
        return j48Result;
    }




    /**
     * 使用十字交叉验证的方法
     * @param folds
     */
    @RequestMapping(value = "/m5p")
    @ResponseBody
    public M5PResult m5p_crossValidation(Integer folds, J48Param j48Param){
        System.out.println("j48Param:"+j48Param.toString()+"----folds"+folds);
        M5PResult m5PResult;
        //第一步：获取数据
        //myInstances=fileUploadController.myInstances;
        Instances instances=instancesDao.getInstances(uniqueID);
        if(instances==null){
            m5PResult=new M5PResult();
            m5PResult.setState(400);
            m5PResult.setError_messaege("门内数据集为空（可能还未上传数据集）");
            return m5PResult;
        }
        //第er步：
        try {
            EvaluationAndClassNameAndClassModel evaluationAndClassNameAndClassModel=myM5P.verificationByCross(instances,10,-9,null);
            classifier=evaluationAndClassNameAndClassModel.getClassifier();//存储模型
            m5PResult=new M5PResult(evaluationAndClassNameAndClassModel);
        } catch (Exception e) {
            e.printStackTrace();
            m5PResult= new M5PResult();
            m5PResult.setState(400);
            m5PResult.setError_messaege(e.toString());
            //System.out.print("训练模型错误："+e.toString());
            return m5PResult;
        }
        return m5PResult;
    }




    @RequestMapping(value = "/uploadPrefile")
    @ResponseBody
    public ResponseInfoEntity uploadPreFile(MultipartFile file){
        ResponseInfoEntity responseInfoEntity=new ResponseInfoEntity();
        if(file.isEmpty()){
            //没有测试数据
            responseInfoEntity.setState(400);
            responseInfoEntity.setError_messaege("上传文件为空！");
            return responseInfoEntity;
        }
        fileName=file.getOriginalFilename();
        SaveFileResult saveFileResult=fileHelpService.saveFile(file,"Classify",ip);
        if(!saveFileResult.getFlag()){
            //保存不成功
            responseInfoEntity.setState(400);
            responseInfoEntity.setError_messaege("出现错误！");
            return responseInfoEntity;
        }
        //第二步：得到Instances对象,设置类别属性（默认最后一列为类别属性）
        ukInstances=new MyInstances(fileHelpService.file2InstancesByFile(file,saveFileResult));
        return responseInfoEntity;
    }

    /**
     * J48算法预测未知数据
     * @param
     */
    @RequestMapping(value = "/prediction")
    @ResponseBody
   public ResponseInfoEntity predictionUkData(){

        ResponseInfoEntity responseInfoEntity=new ResponseInfoEntity();
        Instances instances=instancesDao.getInstances(uniqueID);
        if(ukInstances==null){
            //带预测数据集为空
            responseInfoEntity.setState(400);
            responseInfoEntity.setError_messaege("还未上传带预测数据！");
            return responseInfoEntity;
        }else{
            //待测试数据集不为空的时候
            if(classifier!=null){
                //模型不为空,直接使用模型预测数据（1、用户上传的模型；2、用户使用训练数据训练的模型）
                try {
                    preInstances=myJ48Prediction.PredictionByModel(ukInstances,classifier,instances);//得到预测结果
                } catch (Exception e) {
                    e.printStackTrace();
                    //带预测数据集为空
                    responseInfoEntity.setState(400);
                    responseInfoEntity.setError_messaege(e.getMessage());
                    return responseInfoEntity;
                }
                logger.info("classifier不为空");
            }else{
                //模型为空
                if(LoadModel!=null){
                    //使用从本地加载的模型
                    logger.info("LoadModel不为空");
                }else{
                    //返回error
                    logger.info("返回error");
                    responseInfoEntity.setState(400);
                    responseInfoEntity.setError_messaege("尚未得到预测模型，无法完成预测！");
                    return responseInfoEntity;
                }
            }
        }
        ukInstances=null;//作GC处理
       return  responseInfoEntity;
   }


    /**
     * 使用M5P预测数据
     */
    @RequestMapping(value="/m5p_prediction")
   @ResponseBody
   public ResponseInfoEntity predictionUkDataByM5P(MultipartFile file)throws Exception{
     ResponseInfoEntity responseInfoEntity=new ResponseInfoEntity();
        Instances instances=instancesDao.getInstances(uniqueID);
        if(ukInstances==null){
            responseInfoEntity.setError_messaege("尚未上传预测数据集！");
            responseInfoEntity.setState(400);
            return responseInfoEntity;
        }else{
            //待预测数据集不为空时
            if(classifier!=null){
                preInstances=myM5PPrediction.Prediction(ukInstances,classifier,instances);
                return responseInfoEntity;
            }else{
                if(LoadModel!=null){
                    //使用本地模型预测
                }else{
                    //返回error
                    logger.info("返回error");
                    responseInfoEntity.setState(400);
                    responseInfoEntity.setError_messaege("尚未得到预测模型，无法完成预测！");
                    return responseInfoEntity;
                }
            }
            ukInstances=null;//GC处理
        }
        return null;
   }




    /**
     *保存
     * @param page
     * @return
     * @throws Exception
     */
   @RequestMapping(value = "/showResult")
   @ResponseBody
   public NewEditTable_DataSet ShowPreResult(int page)throws Exception{
       NewEditTable_DataSet newEditTable_dataSet=new NewEditTable_DataSet(null);
       EditTable_DataSet editTable_dataSet=null;
        if(preInstances!=null){
            System.out.println(preInstances);
            newEditTable_dataSet=tablePageService.getOnePage(preInstances,page);
            return newEditTable_dataSet;
            //preInstances的GC处理
        }else{
            //并不存在预测结果集
            editTable_dataSet= new EditTable_DataSet();
            editTable_dataSet.setState(400);
            editTable_dataSet.setError_messaege("不存在预测结果集！");
            newEditTable_dataSet.setEditTable_dataSet(editTable_dataSet);
            return  newEditTable_dataSet;
        }
   }


    /**
     * 保存模型
     */
   @RequestMapping(value = "/saveModel")
   @ResponseBody
   public ResponseInfoEntity SaveModel(HttpServletResponse response, HttpServletRequest request,String modelName){
       //classifier模型变量
      ResponseInfoEntity responseInfoEntity=new ResponseInfoEntity();
       if(classifier!=null){
       String name=classifier.getClass().getName();
           if(!name.contains(modelName)){
               responseInfoEntity.setError_messaege("模型尚不存在！");
               responseInfoEntity.setState(400);
               return responseInfoEntity;
           }
           //模型不为空
           InputStream fileInputStream= null;
           try {
               fileInputStream = modelGen.SaveModel(classifier,modelName);
               if(fileInputStream!=null){
                   response.setHeader("Content-Disposition", "attachment; filename="+modelName+".model");
                   IOUtils.copyLarge(fileInputStream,response.getOutputStream());
                   response.flushBuffer();
               }else{
                   //读取失败
         System.out.println("出现错误");
               }
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               if (fileInputStream != null) {
                   try {
                       fileInputStream.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
       }else{
           //模型为空
           responseInfoEntity.setError_messaege("尚不存在已经训练成功的模型！");
           responseInfoEntity.setState(400);
       }
       return responseInfoEntity;
   }





    /**
     *保存预测结果
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/Savepreresulr")
    @ResponseBody
   public ResponseInfoEntity SavePreResult(HttpServletResponse response, HttpServletRequest request){
       ResponseInfoEntity responseInfoEntity=new ResponseInfoEntity();
       if(preInstances==null){
           responseInfoEntity.setState(400);
           responseInfoEntity.setError_messaege("错误！！");
           return responseInfoEntity;
       }
        InputStream myfis = null;
       try {
           myfis=fileHelpService.file_save(preInstances,fileName,ip);
           response.setHeader("Content-Disposition", "attachment; filename="+fileName);
           IOUtils.copyLarge(myfis,response.getOutputStream());
           response.flushBuffer();
       } catch (Exception e) {
           e.printStackTrace();
           responseInfoEntity.setState(400);
           responseInfoEntity.setError_messaege("出现异常！！");
           return responseInfoEntity;
       }finally {
           if (myfis != null) {
               try {
                   myfis.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       return responseInfoEntity;
   }

}
