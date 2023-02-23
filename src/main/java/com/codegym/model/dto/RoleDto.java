package com.codegym.model.dto;

import java.util.Collection;

public class RoleDto {
    private Long id;
    private String name;
    private String desc;
    Collection<UserDto> userDtos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Collection<UserDto> getUserDtos() {
        return userDtos;
    }

    public void setUserDtos(Collection<UserDto> userDtos) {
        this.userDtos = userDtos;
    }
}
