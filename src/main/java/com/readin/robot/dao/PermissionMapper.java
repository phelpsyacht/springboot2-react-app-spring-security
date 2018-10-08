package com.readin.robot.dao;

import java.util.List;

import com.readin.robot.mapping.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);
    
    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> permissionAll();
    
    List<Permission> permissionByUserId(Integer userId);
    
    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}