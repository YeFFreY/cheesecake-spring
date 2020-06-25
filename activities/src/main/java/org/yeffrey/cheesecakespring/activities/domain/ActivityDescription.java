package org.yeffrey.cheesecakespring.activities.domain;

import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class ActivityDescription implements Serializable {
    private final String value;

    private ActivityDescription(String value) {
        this.value = value;
    }

    public static ActivityDescription from(String value) {
        checkArgument(Strings.isNotBlank(value));
        return new ActivityDescription(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityDescription that = (ActivityDescription) o;
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
