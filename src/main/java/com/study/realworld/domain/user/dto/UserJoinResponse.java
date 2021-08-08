package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Name;
import com.study.realworld.domain.user.domain.User;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public final class UserJoinResponse {

    @JsonProperty("username")
    private Name username;

    @JsonProperty("email")
    private Email email;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("image")
    private String image;

    public static final UserJoinResponse fromUser(final User user) {
        return new UserJoinResponse(user);
    }

    UserJoinResponse() {
    }

    private UserJoinResponse(final User user) {
        this.email = user.email();
        this.username = user.username();
        this.bio = user.bio();
        this.image = user.image();
    }

}
