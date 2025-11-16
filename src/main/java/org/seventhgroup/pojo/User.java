package org.seventhgroup.pojo;

import org.springframework.stereotype.Component;

@Component(value = "user")
public class User {
    private int id;
    private String name;
    private String password;

    public  void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
