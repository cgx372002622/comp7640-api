package com.hkbu.comp7640.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.hkbu.comp7640.bean.Qiniu;
import com.hkbu.comp7640.response.ResponseEnum;
import com.hkbu.comp7640.response.ServerResponseEntity;
import com.qiniu.http.Response;
import com.qiniu.util.Auth;
import com.qiniu.storage.UploadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private Qiniu qiniu;

    @Autowired
    private Auth auth;

    @Autowired
    private UploadManager uploadManager;

    @PostMapping("/upload")
    public ServerResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ServerResponseEntity.success();
        }
        String extName = FileUtil.extName(file.getOriginalFilename());
        String fileName = DateUtil.format(new Date(), "yyyy/MM/") + IdUtil.simpleUUID() + "." + extName;
        String uploadToken = auth.uploadToken(qiniu.getBucket(), fileName);
        Response response = uploadManager.put(file.getBytes(), fileName, uploadToken);
        boolean success = response.isOK();
        return success ? ServerResponseEntity.success(qiniu.getResourcesUrl() + fileName) :
                ServerResponseEntity.success(ResponseEnum.UPLOAD_FILE_FAILED);
    }

}
