package org.yeffrey.cheesecakespring.library.domain;

import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class ActivityName implements Serializable {
    private final String value;

    private ActivityName(String value) {
        this.value = value;
    }

    public static ActivityName from(String value) {
        checkArgument(Strings.isNotBlank(value));
        checkArgument(value.length() <= 255);
        return new ActivityName(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityName)) return false;
        ActivityName that = (ActivityName) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String asString() {
        return this.value;
    }
}
