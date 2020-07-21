package org.yeffrey.cheesecakespring.users.domain;


import com.google.common.base.Preconditions;
import org.apache.logging.log4j.util.Strings;
import org.yeffrey.cheesecakespring.common.domain.BaseDomain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private String username; // FIXME will be immutable

    //TODO add a "display name" which can be modified by user, its first value will be the username

    @NotNull
    private String password;

    @Email
    @NotNull
    private String email;

    protected User() {
    }

    private User(String username,
                 String password,
                 String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public static User from(String username,
                            String password,
                            String email) {
        Preconditions.checkArgument(Strings.isNotBlank(username));
        Preconditions.checkArgument(Strings.isNotBlank(password));
        Preconditions.checkArgument(Strings.isNotBlank(email));
        return new User(username, password, email);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
