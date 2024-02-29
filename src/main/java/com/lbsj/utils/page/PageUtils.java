package com.lbsj.utils.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PageUtils {
    public static <T, M> IPage<M> copyPage(IPage<T> source, TranslateEntity<T, M> translate) {
        IPage<M> target = new Page<>();
        target.setPages(source.getPages());
        target.setCurrent(source.getCurrent());
        target.setSize(source.getSize());
        target.setTotal(source.getTotal());
        List<M> targetRecords = source.getRecords().stream().map(translate::trans).collect(Collectors.toList());
        target.setRecords(targetRecords);
        return target;
    }

    @SneakyThrows
    public static <T, M> IPage<M> cover2Page(IPage<M> source, Class<T> destClass) {
        IPage target = new Page<>();
        target.setPages(source.getPages());
        target.setCurrent(source.getCurrent());
        target.setSize(source.getSize());
        target.setTotal(source.getTotal());
        List<T> list = cover2List(destClass, source.getRecords());
        target.setRecords(list);
        return target;
    }

    @SneakyThrows
    public static <T> List<T> cover2List(Class<T> destClass, List<?> sourceList) {
        List<T> list = new ArrayList<>();
        for (Object record : sourceList) {
            T t = destClass.newInstance();
            BeanUtils.copyProperties(record, t);
            list.add(t);
        }
        return list;
    }
}
