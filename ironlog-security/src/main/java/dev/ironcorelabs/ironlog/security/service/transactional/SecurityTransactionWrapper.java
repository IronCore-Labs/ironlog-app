package dev.ironcorelabs.ironlog.security.service.transactional;

import dev.ironcorelabs.ironlog.security.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityTransactionWrapper {
    private final RefreshTokenService tokenService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void revokeForce(UUID jti) {
        tokenService.logout(jti);
    }
}
