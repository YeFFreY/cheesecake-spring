package org.yeffrey.cheesecakespring.users.dto;

import org.yeffrey.cheesecakespring.common.dto.Command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginCommand extends Command {

    @NotNull
    @Size(min = 3, max = 100)
    public String username;

    @NotNull
    @Size(min = 3, max = 100)
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
