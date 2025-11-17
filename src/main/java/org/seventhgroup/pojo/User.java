package org.seventhgroup.pojo;

import org.seventhgroup.pojo.enums.Role;
import org.seventhgroup.pojo.enums.UserStatus;

/**
 * @author oxygen
 * @time 2025.11.16
 * */

public class User {
    private Long id;
    private String name;
    private String passwordHash;
    private String passwordSalt;
    private String email;
    private Role role;
    private UserStatus status;
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

}
