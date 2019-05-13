package com.seckill.service;

import com.seckill.dao.UserDao;
import com.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getById(int id) {
        return userDao.getById(id);
    }
}
