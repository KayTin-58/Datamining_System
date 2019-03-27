package com.zhang.utils.fileUtils;

/**
 * Created by 直到世界尽头 on 2018/3/19.
 */
public class FileUtils {


    /**
     * 获取文件的名称
     * @param file
     * @return
     */
    public static String getFileName(String file){
        String file_name=null;
        file_name= file.substring(0,file.lastIndexOf(".")).toLowerCase();
        return file_name;
    }

    /**
     * 获取文件的类型
     * @param file
     * @return
     */
    public static String getFileType(String file){
        String file_type=null;
        file_type=file.substring(file.lastIndexOf(".") + 1).toLowerCase();

        return file_type;
    }
}
