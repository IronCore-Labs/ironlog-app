package dev.ironcorelabs.ironlog.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ironcorelabs.ironlog.restapi.openapi.model.ClientDto;

public class ClientDTO extends ClientDto {
    private Long id;

    public ClientDTO id(Long id) {
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
