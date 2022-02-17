package com.example.common.lang.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author giaming
 * @date 2022/2/12 - 15:31
 * @detail
 */
@Slf4j
@Data
public class LoginDto extends AbstractMethodError implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
