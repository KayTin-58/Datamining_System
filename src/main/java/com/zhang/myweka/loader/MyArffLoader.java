package com.zhang.myweka.loader;

import weka.core.converters.ArffLoader;

import java.io.*;

/**
 * Created by 直到世界尽头 on 2018/3/15.
 */
public class MyArffLoader extends ArffLoader{

    @Override
    public void setSource(InputStream in) throws IOException {
        this.m_File = (new File(System.getProperty("user.dir"))).getAbsolutePath();
        this.m_URL = "http://";
        this.m_sourceReader = new BufferedReader(new InputStreamReader(in,"cp936"));
    }
}
