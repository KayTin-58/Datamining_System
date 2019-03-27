package com.zhang.service.pageService;

import com.zhang.dto.NewEditTable_DataSet;
import weka.core.Instances;

/**
 * 分页
 * Created by 直到世界尽头 on 2018/4/11.
 */
public interface TablePageService {


    /**
     * 默认每页显示88条数据
     * @param instances
     * @param page
     * @return
     * @throws Exception
     */
    public NewEditTable_DataSet getOnePage(Instances instances, int page)throws Exception;
}
