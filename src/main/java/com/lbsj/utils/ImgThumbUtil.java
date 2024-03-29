package com.lbsj.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class ImgThumbUtil {
    /**
     * 强制压缩/放大图片到固定的大小
     * @param fileName 压缩文件("c:\\1.png")
     * @param compressPercent 压缩比例(压缩为原来一半传0.5)
     * @return
     */
    public static synchronized BufferedImage resize(String fileName,double compressPercent){
        BufferedImage img = null;//原图
        BufferedImage compressImg = null;//压缩后图
        int width,height;
        try {
            File file = new File(fileName);
            log.info(fileName);
            img = ImageIO.read(file);
            width = (int)(img.getWidth()*compressPercent);
            height = (int)(img.getHeight()*compressPercent);
            compressImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
            compressImg.getGraphics().drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图
        } catch (IOException e) {
            log.error("",e);
        }
        return compressImg;
    }

    public static BufferedImage resize(BufferedImage image,double compressPercent){
        int width,height;
        width = (int)(image.getWidth()*compressPercent);
        height = (int)(image.getHeight()*compressPercent);
        BufferedImage compressImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
        compressImg.getGraphics().drawImage(image, 0, 0, width, height, null); // 绘制缩小后的图
        return compressImg;
    }

    /**
     *
     * @param file 存放位置
     * @param img
     */
    public static void writeToFile(String file,BufferedImage img){
        FileOutputStream out = null;
        try {
            File destFile = new File(file);
            // 输出到文件流
            out = new FileOutputStream(destFile);
            ImageIO.write(img, "png", out);

        } catch (Exception e) {
            log.info("",e);
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                log.info("",e);
            }
        }
    }
}
