package com.example.test.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
public class Users {

    private List<User> userList;

    public Users() {
    }

    public Users(List<User> user) {
        this.userList = user;
    }

    public List<User> getUsers() {
        return userList;
    }
    @XmlElement(name = "user")
    public void setUsers(List<User> user) {
        this.userList = user;
    }
}
