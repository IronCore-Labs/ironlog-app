package dev.ironcorelabs.ironlog.security.service.impl;

import dev.ironcorelabs.ironlog.core.config.MessageConfig;
import dev.ironcorelabs.ironlog.security.mapper.UserMapper;
import dev.ironcorelabs.ironlog.security.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final MessageConfig messageConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mapper.toUserDetail(repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(messageConfig
                        .messageSource()
                        .getMessage("username.not.found", new Object[]{username}, LocaleContextHolder.getLocale()))));
    }
}
