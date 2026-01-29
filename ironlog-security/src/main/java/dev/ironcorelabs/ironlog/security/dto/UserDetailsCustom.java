package dev.ironcorelabs.ironlog.security.dto;

import dev.ironcorelabs.ironlog.core.security.AuthenticateUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsCustom implements UserDetails, AuthenticateUser {

    private String username;
    private String password;
    private Long id;

    private List<GrantedAuthority> authorities;
}
