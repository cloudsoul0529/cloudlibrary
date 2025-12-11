package org.seventhgroup.pojo;

import java.io.Serializable;

/**
 * @author oxygen
 * @time 2025.11.16
 * */
public class User implements Serializable {
    public static final String ACTIVE = "0";//正常状态
    public static final String DELETED = "1";//注销状态
    //用户id
    private Integer id;
    //用户名称
    private String name;
    //密码盐值
    private String passwordSalt;
    //密码哈希值
    private String passwordHash;
    //密码
    private String password;
    //用户邮箱
    private String email;
    //用户角色（管理员："ADMIN"，工作人员："WORKER"，普通用户："USER"）
    private String role;
    //用户状态
    private String status;
    //注册时间
    private String createdate;
    //注销时间
    private String deletedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(String deletedate) {
        this.deletedate = deletedate;
    }

}