package com.lbsj.utils;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

public class GenerateUtils {

    @SneakyThrows
    public static <T> T cover2Bean(Object source, Class<T> clazz) {
        T t = clazz.newInstance();
        BeanUtils.copyProperties(source, t);
        return t;
    }
}
