package com.zhang.myweka.instances_op.impl;

import com.zhang.entity.AttributeNameAndType;
import com.zhang.entity.InstancesEntityFirst;
import com.zhang.myweka.instances_op.Instances2Entity;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by 直到世界尽头 on 2018/3/16.
 */
@Service
public class Instances2EntityImpl implements Instances2Entity {

    @Override
    public InstancesEntityFirst instances2EntityForst(Instances instances,String fileName) {
        InstancesEntityFirst instancesEntityFirst=new InstancesEntityFirst();

        ArrayList<AttributeNameAndType> attribute_list=new ArrayList<AttributeNameAndType>();//

        int numAttributes=instances.numAttributes();
        int numInstances=instances.numInstances();

        Enumeration<Attribute> attributeEnum= instances.enumerateAttributes();

        Attribute attribute=null;
        String attributeName=null;
        int type=0;
        AttributeNameAndType attributeNameAndType=null;

        while(attributeEnum.hasMoreElements()){
            attribute =(Attribute)attributeEnum .nextElement();

            attributeName=attribute.name();
            type=attribute.type();
            attributeNameAndType=new AttributeNameAndType(attributeName,type);
            attribute_list.add(attributeNameAndType);
        }

        instancesEntityFirst.setAttribute_list(attribute_list);
        instancesEntityFirst.setAttributes_size(numAttributes);
        instancesEntityFirst.setInstance_size(numInstances);
        instancesEntityFirst.setFile_name(fileName);
        return instancesEntityFirst;
    }
}
