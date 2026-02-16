package dev.ironcorelabs.ironlog.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsCustom implements UserDetails {

    private String username;
    private String password;
    private Long id;
    private UUID externalId;

    private List<? extends GrantedAuthority> authorities;
}
