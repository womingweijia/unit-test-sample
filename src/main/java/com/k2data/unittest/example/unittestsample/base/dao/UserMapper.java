package com.k2data.unittest.example.unittestsample.base.dao;

import com.k2data.unittest.example.unittestsample.base.dao.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //手动增加
    List<User> selectAllUsers();
    int chectExist(Integer id);
}