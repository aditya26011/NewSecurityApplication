package com.coding.security.securityApplication.dto;


import com.coding.security.securityApplication.entity.enums.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

   private String email;
   private String password;
   private String name;
   private Set<Roles> roles;
}
