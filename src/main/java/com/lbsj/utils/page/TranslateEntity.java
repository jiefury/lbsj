package com.lbsj.utils.page;

@FunctionalInterface
public interface TranslateEntity<T,M> {
    M trans(T source);
}
