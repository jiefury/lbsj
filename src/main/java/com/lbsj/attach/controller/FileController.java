package com.lbsj.attach.controller;

import com.lbsj.common.annotation.FileRecord;
import com.lbsj.common.model.FileVO;
import com.lbsj.common.model.RequestResult;
import com.lbsj.common.model.StatusEnum;
import com.lbsj.config.SysParamsConfig;
import com.lbsj.utils.CommonUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @PostMapping("/upload")
    @FileRecord
    public RequestResult<FileVO> upload(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            String reFileName = CommonUtil.uuid() + "." + suffix;
            String path = SysParamsConfig.TEMP_DIR_PATH + reFileName;
            File uploadFile = new File(path);
            if (uploadFile.exists()) {
                uploadFile.delete();
            }
            boolean b = uploadFile.createNewFile();
            if (b) {
                file.transferTo(uploadFile);
                FileVO vo = new FileVO();
                vo.setFileName(filename);
                vo.setFileUrl(reFileName);
                vo.setRefileName(reFileName);
                vo.setSuffix(suffix);
                return RequestResult.e(StatusEnum.OK, vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RequestResult.e(StatusEnum.FAIL);
        }
        return RequestResult.e(StatusEnum.FAIL);
    }

    @RequestMapping("/download")
    public void download(String fileName, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/force-download");
        String path = SysParamsConfig.TEMP_DIR_PATH + fileName;
        File uploadFile = new File(path);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(uploadFile);
            IOUtils.copy(inputStream, response.getOutputStream(), 1024);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                IOUtils.close(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
