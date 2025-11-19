package org.seventhgroup.pojo.enums;

/**
 * @author oxygen
 * @time 2025.11.17
 */
public enum BookStatus {
    AVAILABLE("可借阅"),
    BORROWED("已借出"),
    MAINTENANCE("维护中"),
    DAMAGED("损坏"),
    LOST("丢失"),
    RESERVED("已被预约"),
    ARCHIVED("已归档");

    private final String desc;

    BookStatus(String desc) {
        this.desc = desc;
    }

    // 获取书的状态
    public String getDesc() {
        return desc;
    }
}