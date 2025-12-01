package com.coding.security.securityApplication.entity;

import com.coding.security.securityApplication.entity.enums.Permissions;
import com.coding.security.securityApplication.entity.enums.Roles;
import com.coding.security.securityApplication.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;

   @ElementCollection(fetch = FetchType.EAGER) //since we are using set we need to use ElementCollection
   @Enumerated(EnumType.STRING)
   private Set<Roles> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities= new HashSet<>();

        roles.forEach(role->{
         Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritiesForRole(role);
         authorities.addAll(permissions);
         authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
        });


        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
