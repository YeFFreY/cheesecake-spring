package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.yeffrey.cheesecakespring.activities.domain.UserId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class UserIdAttributeConverter implements AttributeConverter<UserId, String> {
    @Override
    public String convertToDatabaseColumn(UserId attribute) {
        return Objects.nonNull(attribute) ? attribute.asString() : null;
    }

    @Override
    public UserId convertToEntityAttribute(String dbData) {
        return UserId.from(dbData);
    }
}
