package com.coding.security.securityApplication.utils;

import com.coding.security.securityApplication.entity.enums.Permissions;
import com.coding.security.securityApplication.entity.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.coding.security.securityApplication.entity.enums.Permissions.*;
import static com.coding.security.securityApplication.entity.enums.Role.*;

public class PermissionMapping {

  private static final   Map<Role, Set<Permissions>>map = Map.of(
            USER, Set.of(USER_VIEW,POST_VIEW),
            CREATOR,Set.of(POST_CREATE,POST_UPDATE,USER_UPDATE),
            ADMIN, Set.of(POST_CREATE,POST_UPDATE,USER_UPDATE,USER_DELETE,USER_CREATE,POST_DELETE)
    );

  public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
    return   map.get(role).stream()
              .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
              .collect(Collectors.toSet());
  }
}
