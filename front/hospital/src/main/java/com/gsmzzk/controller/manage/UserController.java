package com.gsmzzk.controller.manage;

import com.gsmzzk.common.Const;
import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user/")
@CrossOrigin
public class UserController {

    @Autowired
    IUserService userService;
    @RequestMapping(value = "login")
    public ServerResponse login(String username, String password, HttpSession session){
        System.out.println("=============login=="+username);

        ServerResponse serverResponse= userService.login(username, password);
        if(serverResponse.isSucess()){
            session.setAttribute(Const.USER,serverResponse.getData());
        }
        return serverResponse;

    }

}
