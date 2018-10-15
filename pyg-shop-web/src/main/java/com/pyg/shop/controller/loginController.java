package com.pyg.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class loginController {
    /**
     *
     * @return
     */
    @RequestMapping("/findName")
    public Map findName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map =new HashMap();
        map.put("loginName",name);
        return map;
    }
}
