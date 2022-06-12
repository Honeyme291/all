package com.project.all.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:file
 * @Author:lxx
 * @Date 2022/6/7 0:02
 **/
public class fileList {
   private static List<String> file=new ArrayList<String>();
    public List<String> userFileList(){
        return file;
    }
    public void clear(){
        file.clear();
    }
}
