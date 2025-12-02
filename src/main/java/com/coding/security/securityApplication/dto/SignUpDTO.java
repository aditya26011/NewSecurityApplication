package com.coding.security.securityApplication.dto;


import com.coding.security.securityApplication.entity.enums.Permissions;
import com.coding.security.securityApplication.entity.enums.Role;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

   private String email;
   private String password;
   private String name;
   private Set<Role> roles;
   private Set<Permissions> permissions;

}
