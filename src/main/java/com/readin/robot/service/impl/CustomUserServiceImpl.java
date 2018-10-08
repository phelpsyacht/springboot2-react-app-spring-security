package com.readin.robot.service.impl;

import com.readin.robot.dao.PermissionMapper;
import com.readin.robot.dao.UserInMapper;
import com.readin.robot.mapping.Permission;
import com.readin.robot.mapping.UserIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("CustomUserServiceImpl")
public class CustomUserServiceImpl implements UserDetailsService { 
	@Autowired
	UserInMapper userInMapper;
    
    @Autowired
    PermissionMapper permission;

    public UserDetails loadUserByUsername(String name) {
       UserIn userIn = userInMapper.findByName(name);
        if (userIn != null) {
            List<Permission> permissions = permission.permissionByUserId(userIn.getLoginId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName()!=null) {
                System.out.println(permission.getName());
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(userIn.getName(), userIn.getPassword(), grantedAuthorities);
        } else {
        	StringBuilder builder = new StringBuilder();
        	builder.append("user");
        	builder.append(name);
        	builder.append("do not exist!");
            throw new UsernameNotFoundException(builder.toString());
        }
    }

}
