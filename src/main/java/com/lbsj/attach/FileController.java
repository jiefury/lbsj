package com.lbsj.attach;

import com.lbsj.common.model.FileVO;
import com.lbsj.common.model.RequestResult;
import com.lbsj.common.model.StatusEnum;
import com.lbsj.config.SysParamsConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @PostMapping("/upload")
    public RequestResult<FileVO> upload(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String subFilePath = SysParamsConfig.getFilePath(filename);
            String path = SysParamsConfig.TEMP_DIR_PATH + subFilePath;
            File uploadFile = new File(path);
            if (uploadFile.exists()) {
                uploadFile.delete();
            }
            boolean b = uploadFile.createNewFile();
            if (b) {
                file.transferTo(uploadFile);
                FileVO vo = new FileVO();
                vo.setFileName(filename);
                vo.setFileUrl(subFilePath);
                return RequestResult.e(StatusEnum.OK, vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RequestResult.e(StatusEnum.FAIL);
        }
        return RequestResult.e(StatusEnum.FAIL);
    }
}
