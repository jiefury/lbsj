package com.lbsj.common.model;

import lombok.Data;


@Data
public class PageDTO {

    private Integer page;

    private Integer pageSize;

    public Integer getPage() {
        if (null == page) {
            return 1;
        }
        return page;
    }

    public Integer getPageSize() {
        if (null == pageSize) {
            return 10;
        }
        return pageSize;
    }

}
