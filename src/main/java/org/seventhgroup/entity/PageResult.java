package org.seventhgroup.entity;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
    private long total; 		// 总数
    private List rows; 		// 返回的数据集合
    public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
    //…省略 getter/setter方法
}
