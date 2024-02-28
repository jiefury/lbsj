package com.lbsj.config;

import com.lbsj.utils.DateUtil;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class SysParamsConfig {

    public static final String TEMP_DIR_PATH;

    static {
        TEMP_DIR_PATH = System.getProperty("user.dir") + File.separator + "file";
        File file = new File(TEMP_DIR_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(50L, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(50L, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

    public static String getFilePath(String fileName) {
        String date = DateUtil.formatDate(null);
        String dir = TEMP_DIR_PATH + File.separator + date;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return File.separator + date + File.separator + fileName;
    }
}
