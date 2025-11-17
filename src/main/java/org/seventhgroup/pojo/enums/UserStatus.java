package org.seventhgroup.pojo.enums;

/**
 *@author oxygen
 *@time 2025.11.16
 */
public enum UserStatus {
    // 正常（可登录、借阅）
    ACTIVE,
    // 禁用（不可登录、借阅，由管理员操作）
    DISABLED,
    // 待审核（适用于需要审核的注册流程，未审核用户不可使用核心功能）
    PENDING
}