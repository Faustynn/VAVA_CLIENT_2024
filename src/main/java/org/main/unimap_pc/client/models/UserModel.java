package org.main.unimap_pc.client.models;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String login;

    @JsonProperty("admin")
    private boolean isAdmin;
    private boolean subscribe;
    private boolean verification;
    private String avatar;

}