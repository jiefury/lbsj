package com.lbsj.config;

import com.sun.jna.Platform;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
@Log4j2
public class SysParamsConfig {


    public static String TEMP_DIR_PATH;

    @Value("${upload.path}")
    public void setTempDirPath(String tempDirPath) {
        boolean linux = Platform.isLinux();
        if (linux) {

        }
        TEMP_DIR_PATH = tempDirPath;
    }

//    static {
////        TEMP_DIR_PATH = System.getProperty("user.dir") + File.separator + "file";
//        File file = new File(TEMP_DIR_PATH);
//        if (!file.exists()) {
//            file.mkdirs();
//            log.info("创建文件目录，成功 {}", file.getAbsolutePath());
//        } else {
//            log.info("文件目录已经存在");
//        }
//    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(50L, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(50L, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

    public static String getFilePath(String fileName) {
//        String date = DateUtil.formatDate(null);
        String dir = TEMP_DIR_PATH + File.separator;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return File.separator + fileName;
    }
}
