package com.zhang.service.pageService.impl;

import com.zhang.dto.NewEditTable_DataSet;
import com.zhang.entity.EditTable_DataSet;
import com.zhang.service.FileHelpService;
import com.zhang.service.pageService.TablePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Instances;

/**
 * Created by 直到世界尽头 on 2018/4/11.
 */
@Service
public class TablePageServiceImpl implements TablePageService{

    @Autowired
    private FileHelpService fileHelpService;

    @Override
    public NewEditTable_DataSet getOnePage(Instances instances, int page) throws Exception {
        //第一页：0-87,88-175
        int start=(page-1)*88;
        int total=instances.numInstances();
        int pageSum=(total-1)/88+1;//总页数
        NewEditTable_DataSet newEditTable_dataSet=null;
        if(page>0&page<=pageSum){
            int end=(page==pageSum) ? (total-1):start+87;
            Instances Onepage=new Instances(instances,0);
            //首先页数要大于0
           for(int i=start;i<=end;i++){
               Onepage.add(instances.instance(i));
           }
      System.out.println(start+"----"+end+"-----"+Onepage.numInstances()+"--"+pageSum);


            EditTable_DataSet editTable_dataSet=fileHelpService.instance2tableDataSet(Onepage);
            newEditTable_dataSet=new NewEditTable_DataSet(editTable_dataSet);
            newEditTable_dataSet.setTotalPage(pageSum);
        }
        return newEditTable_dataSet;
    }
}
