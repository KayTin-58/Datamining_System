package com.zhang.myweka.instances_op;

import com.zhang.entity.InstancesEntityFirst;
import weka.core.Instances;

/**
 * Created by 直到世界尽头 on 2018/3/16.
 */
public interface Instances2Entity {


    /**
     * Instances转化成EntityForst
     * @param instances
     * @return
     */
    public InstancesEntityFirst instances2EntityForst(Instances instances,String fileName);
}
