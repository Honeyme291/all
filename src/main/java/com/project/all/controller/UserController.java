package com.project.all.controller;

import com.project.all.domain.User;
import com.project.all.domain.fileList;
import com.project.all.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserController {

    @Resource
    private UserService userService;

    //文件位置
    private static final String position="F:\\Java程序\\all\\upload\\";

    /**
     * 注册账号
     *
     * @param user
     * @return
     */
    @PostMapping("create")
    public Map<String, Object> createAccount(User user) {
        return userService.createAccount(user);
    }

    /**
     * 登录账号
     *
     * @param user
     * @return
     */
    @PostMapping("login")
    public Map<String, Object> loginAccount(User user) {
        return userService.loginAccount(user);
    }

    /**
     * 激活账号
     *
     * @param confirmCode
     * @return
     */
    @GetMapping("activation")
    public Map<String, Object> activationAccont(String confirmCode) {
        return userService.activationAccont(confirmCode);
    }


    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam MultipartFile file) {
            // 获取文件的名称
            String fileName = file.getOriginalFilename();
            try {
                // 新建一个文件路径
                File uploadFile = new File(position + fileName);
                // 当父级目录不存在时，自动创建
                if (!uploadFile.getParentFile().exists()) {
                    uploadFile.getParentFile().mkdirs();
                }
                // 存储文件到电脑磁盘
                file.transferTo(uploadFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("url", "http://localhost:8080/users/download?fileName=" + fileName);
           fileList filelist=new fileList();
           filelist.userFileList().add("F:\\Java程序\\all\\upload\\"+fileName);
            return map;
        }
    @GetMapping("/download")
    public void download(@RequestParam String fileName, HttpServletResponse response) {
        //  新建文件流，从磁盘读取文件流
        try (FileInputStream fis = new FileInputStream(position + fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = response.getOutputStream()) {    //  OutputStream 是文件写出流，讲文件下载到浏览器客户端
            // 新建字节数组，长度是文件的大小，比如文件 6kb, bis.available() = 1024 * 6
            byte[] bytes = new byte[bis.available()];
            // 从文件流读取字节到字节数组中
            bis.read(bytes);
            // 重置 response
            response.reset();
            // 设置 response 的下载响应头
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 注意，这里要设置文件名的编码，否则中文的文件名下载后不显示
            // 写出字节数组到输出流
            os.write(bytes);
            // 刷新输出流
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

