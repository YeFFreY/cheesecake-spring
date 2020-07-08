package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.yeffrey.cheesecakespring.library.domain.ActivityDescription;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class ActivityDescriptionAttributeConverter implements AttributeConverter<ActivityDescription, String> {
    @Override
    public String convertToDatabaseColumn(ActivityDescription attribute) {
        return Objects.nonNull(attribute) ? attribute.asString() : null;
    }

    @Override
    public ActivityDescription convertToEntityAttribute(String dbData) {
        return ActivityDescription.from(dbData);
    }
}
