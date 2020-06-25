package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.yeffrey.cheesecakespring.activities.domain.ActivityName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActivityNameAttributeConverter implements AttributeConverter<ActivityName, String> {
    @Override
    public String convertToDatabaseColumn(ActivityName attribute) {
        return attribute.asString();
    }

    @Override
    public ActivityName convertToEntityAttribute(String dbData) {
        return ActivityName.from(dbData);
    }
}
