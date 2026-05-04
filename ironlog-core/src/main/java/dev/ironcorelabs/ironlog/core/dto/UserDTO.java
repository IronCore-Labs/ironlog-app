package dev.ironcorelabs.ironlog.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ironcorelabs.ironlog.restapi.openapi.model.User;

public class UserDTO extends User {

    private Long id;

    public UserDTO id(Long id) {
        this.id = id;
        return this;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
