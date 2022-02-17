package com.example.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author giaming
 * @date 2022/2/12 - 15:10
 * @detail
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String jwt){
        this.token = jwt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
