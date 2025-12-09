package org.seventhgroup.dto;

//DTO封装密码盐与哈希值
public class PasswordResult {
    private String passwordSalt;
    private String passwordHash;
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
}
