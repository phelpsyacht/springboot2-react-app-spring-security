package com.readin.robot.dao;

import com.readin.robot.mapping.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    SysUser findByUserName(String username);
    
    int updateByPrimaryKey(SysUser record);
}