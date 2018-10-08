package com.readin.robot.dao;

import com.readin.robot.mapping.PnRole;

public interface PnRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PnRole record);

    int insertSelective(PnRole record);

    PnRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PnRole record);

    int updateByPrimaryKey(PnRole record);
}