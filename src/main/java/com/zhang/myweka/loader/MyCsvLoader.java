package com.zhang.myweka.loader;

import weka.core.converters.CSVLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 直到世界尽头 on 2018/3/15.
 */
public class MyCsvLoader extends CSVLoader {
    @Override
    public void setSource(InputStream input) throws IOException {
        //super.setSource(input);
        this.m_structure = null;
        this.m_sourceFile = null;
        this.m_File = null;
        this.m_sourceReader = new BufferedReader(new InputStreamReader(input,"cp936"));
    }
}
