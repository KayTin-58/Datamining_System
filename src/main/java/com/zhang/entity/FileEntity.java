package com.zhang.entity;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 直到世界尽头 on 2018/3/15.
 */
public class FileEntity {

    private String OriginalFilename;
    private long size;
    private String ContentType;
    private MultipartFile file;
    private String file_type;
    private String fileNmae;

    public FileEntity(MultipartFile file) {
        this.OriginalFilename=file.getOriginalFilename();
        this.ContentType=file.getContentType();
        this.size=file.getSize();
        this.file_type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        this.fileNmae=file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
    }


    public String getOriginalFilename() {
        return OriginalFilename;
    }

    public long getSize() {
        return size;
    }

    public String getContentType() {
        return ContentType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getFile_type() {
        return file_type;
    }

    public String getFileNmae() {
        return fileNmae;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "OriginalFilename='" + OriginalFilename + '\'' +
                ", size=" + size +
                ", ContentType='" + ContentType + '\'' +
                '}';
    }
}
