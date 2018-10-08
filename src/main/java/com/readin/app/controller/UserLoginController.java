package com.readin.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.readin.component.Security.JWTAuthentication;
import com.readin.component.Security.Credential;
import com.readin.robot.mapping.UserIn;
import com.readin.robot.service.UserInService;


/**
 * 
 * 类名称：LoginController<br>
 * 类描述：登录、注册页面控制<br>
 * 创建人：readin<br>
 *
 */
@RestController
public class UserLoginController   {
	 @Autowired
     private UserInService userInService;
    /**
     * 用户
     */
	private UserIn userIn;
	 
   @RequestMapping(value = { "/login", "/login/" }, method = RequestMethod.GET)
    public String login() {
	   //CsrfRequestDataValueProcessor csrfRequestDataValueProcessor = new CsrfRequestDataValueProcessor();
        /*User user = getUser();
        if (user != null && StringUtils.isNotBlank(user.getId())) {
            return redirectTo("/");
        }*/
        return "ok";
    }
  
    /**
     * 注册接口
     * @param user
     * @return
     */
    @RequestMapping(value = { "/regin", "/regin/" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> reg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<>();   
      //you should do some  things...
        
        Credential cred = new ObjectMapper().readValue(request.getInputStream(), Credential.class);
    	String name = cred.getName();
    	System.out.println(name);
        boolean userBool = userInService.isExistName(name);
        if (userBool){
             result.put("status", 2);
             result.put("msg", "can not use");
             return result;
        } 
        UserIn userIn = new UserIn();
    	userIn.setName(name);
    	String rawPassWord = cred.getPassword();
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
        String password = passwordEncoder.encode(rawPassWord);
        userIn.setPassword(password); 
        userInService.save(userIn);    
    	boolean bool = userInService.isExistName(name);
        if (bool){
            //you should do some other things...

        	String str = JWTAuthentication.tokenStr(name);
        	result.put("status", 0);
        	result.put("result", str);
        	result.put("msg", "注册成功");
        }else {
        	 result.put("status", 1);
             result.put("msg", "注册失败");
        } 
        
        return result;
    }
}
