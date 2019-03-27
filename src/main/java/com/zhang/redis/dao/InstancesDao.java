package com.zhang.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import weka.core.Instances;

import javax.annotation.Resource;

/**
 * Created by 直到世界尽头 on 2018/4/21.
 */
@Repository
public class InstancesDao {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name="stringRedisTemplate")
    ValueOperations<String,String> valueOpsStr;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;


    @Resource(name="redisTemplate")
    ValueOperations<Object,Object> valueOps;


    public void save(Instances instances,String uniqueID){
        valueOps.set(uniqueID,instances);
    }


    public Instances getInstances(String uniqueID){
        return (Instances)valueOps.get(uniqueID);
    }
}
