package org.yeffrey.cheesecakespring.infrastructure.persistence.converters;

import org.yeffrey.cheesecakespring.library.domain.UserId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
class UserIdAttributeConverter implements AttributeConverter<UserId, String> {
    @Override
    public String convertToDatabaseColumn(UserId attribute) {
        return Objects.nonNull(attribute) ? attribute.asString() : null;
    }

    @Override
    public UserId convertToEntityAttribute(String dbData) {
        return UserId.from(dbData);
    }
}
