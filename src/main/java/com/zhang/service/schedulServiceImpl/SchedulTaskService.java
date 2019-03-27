package com.zhang.service.schedulServiceImpl;

import com.zhang.entity.constantEntity.ConstantEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 计划任务
 * Created by 直到世界尽头 on 2018/3/30.
 */
@Service
public class SchedulTaskService {
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron="0 59 23 ? * *")
    public void clearFile(){
        File file=new File(ConstantEntity.Save_File_Path);
        if(file.exists()){
            if(!file.isDirectory()){
                file.delete();
            }else if(file.isDirectory()){
                String[] file_list=file.list();
                for(int i=0;i<file_list.length;i++){
                   File deletefile=new File(ConstantEntity.Save_File_Path+"\\"+file_list[i]);
                    if(!deletefile.isDirectory()){
                        deletefile.delete();
                        deletefile=null;
                    }
                }
            }
        }
    }
}
