package com.zhang.pojo;

/**
 * Created by 直到世界尽头 on 2018/3/15.
 */
public class SaveFileResult {
    private String file_path;
    private Boolean flag;

    public SaveFileResult(String file_path, Boolean flag) {
        this.file_path = file_path;
        this.flag = flag;
    }

    public Boolean getFlag() {
        return flag;
    }

    public String getFile_path() {
        return file_path;
    }

    @Override
    public String toString() {
        return "SaveFileResult{" +
                "file_path='" + file_path + '\'' +
                ", flag=" + flag +
                '}';
    }
}
