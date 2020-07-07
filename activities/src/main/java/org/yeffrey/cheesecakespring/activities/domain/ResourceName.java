package org.yeffrey.cheesecakespring.activities.domain;

import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class ResourceName implements Serializable {
    private final String value;

    private ResourceName(String value) {
        this.value = value;
    }

    public static ResourceName from(String value) {
        checkArgument(Strings.isNotBlank(value));
        checkArgument(value.length() <= 255);
        return new ResourceName(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceName)) return false;
        ResourceName that = (ResourceName) o;
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
