package com.example.test;

import com.example.test.entity.User;
import com.example.test.entity.Users;

import java.util.Comparator;
import java.util.List;


public class UsersContainer {

    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public static UsersContainer fromUsers(Users users) {
        UsersContainer usersContainer = new UsersContainer();
        usersContainer.setUserList(users.getUsers());
        return usersContainer;
    }

    public void add(User user) {
        userList.sort(Comparator.comparingInt(User::getId));
        int newId = userList.get(userList.size() - 1).getId();
        user.setId(++newId);
        userList.add(user);
    }

}
