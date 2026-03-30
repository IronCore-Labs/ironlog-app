package dev.ironcorelabs.ironlog.core.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserStatusEvent extends ApplicationEvent {

    private Long userId;
    private Boolean enabled;

    public UserStatusEvent(Object source, Long userId, Boolean enabled) {
        super(source);
        this.userId = userId;
        this.enabled = enabled;
    }
}
