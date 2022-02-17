package com.example.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author giaming
 * @date 2022/2/12 - 15:07
 * @detail
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;
    private String username;
    private String avatar;
    private String role;
}
