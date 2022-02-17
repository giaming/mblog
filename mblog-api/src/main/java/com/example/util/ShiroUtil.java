package com.example.util;

import com.example.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author giaming
 * @date 2022/2/12 - 15:05
 * @detail
 */
public class ShiroUtil {
    public static AccountProfile getProfile(){

        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
