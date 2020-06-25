package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.yeffrey.cheesecakespring.activities.domain.ActivityDescription;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActivityDescriptionAttributeConverter implements AttributeConverter<ActivityDescription, String> {
    @Override
    public String convertToDatabaseColumn(ActivityDescription attribute) {
        return attribute.asString();
    }

    @Override
    public ActivityDescription convertToEntityAttribute(String dbData) {
        return ActivityDescription.from(dbData);
    }
}
