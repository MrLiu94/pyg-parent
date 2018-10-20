package com.pyg.manager.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbSeller;
import com.pyg.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        //得到商家对象
        TbSeller tbSeller = sellerService.findOne(username);

        if (tbSeller != null) {
            if (tbSeller.getStatus().equals("1")) {
                return new User(username, tbSeller.getPassword(), list);
            }else {
                return null;
            }
        }else {
            return null;
        }

    }
}
