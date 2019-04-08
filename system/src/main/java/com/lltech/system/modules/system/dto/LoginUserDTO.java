package com.lltech.system.modules.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author CREATED BY L.C.Y on 2019-4-4
 */
@Data
@Accessors(chain = true)
public class LoginUserDTO {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "{ username = " + username  + ", password = ****** }";
    }
}
