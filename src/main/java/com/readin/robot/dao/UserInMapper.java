package com.readin.robot.dao;

import com.readin.robot.mapping.UserIn;

public interface UserInMapper {
    int deleteByPrimaryKey(Integer loginId);

    int insert(UserIn record);

    int insertSelective(UserIn record);

    UserIn selectByPrimaryKey(Integer loginId);
    
    UserIn findByName(String name);
    
    int updateByPrimaryKeySelective(UserIn record);

    int updateByPrimaryKey(UserIn record);


}