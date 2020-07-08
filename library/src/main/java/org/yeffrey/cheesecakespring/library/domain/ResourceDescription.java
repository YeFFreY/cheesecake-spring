package org.yeffrey.cheesecakespring.library.domain;

import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class ResourceDescription implements Serializable {
    private final String value;

    private ResourceDescription(String value) {
        this.value = value;
    }

    public static ResourceDescription from(String value) {
        checkArgument(Strings.isNotBlank(value));
        return new ResourceDescription(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceDescription)) return false;
        ResourceDescription that = (ResourceDescription) o;
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
