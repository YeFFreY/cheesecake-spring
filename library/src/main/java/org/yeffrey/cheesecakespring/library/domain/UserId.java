package org.yeffrey.cheesecakespring.library.domain;

import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class UserId implements Serializable {
    private final String value;

    private UserId(String value) {
        this.value = value;
    }

    public static UserId from(String value) {
        checkArgument(Strings.isNotBlank(value));
        return new UserId(value);
    }

    public String asString() {
        return this.value;
    }

    public static UserId system() {
        return new UserId("system");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId = (UserId) o;
        return value.equals(userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
