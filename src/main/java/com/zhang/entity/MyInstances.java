package com.zhang.entity;

import weka.core.Instances;

import java.io.IOException;
import java.io.Reader;

/**
 * 文件解析后的对象
 * Created by 直到世界尽头 on 2018/4/5.
 */
//@Component
//@Scope("session")
//@Lazy(true)
public class MyInstances extends Instances {

    public MyInstances(Reader reader) throws IOException {
       super(reader);
    }

    /** @deprecated */
    @Deprecated
    public MyInstances(Reader reader, int capacity) throws IOException {
      super(reader,capacity);
    }

    public MyInstances(Instances dataset) {
      super(dataset);
    }

    public MyInstances(Instances dataset, int capacity) {
       super(dataset,capacity);
    }

}
