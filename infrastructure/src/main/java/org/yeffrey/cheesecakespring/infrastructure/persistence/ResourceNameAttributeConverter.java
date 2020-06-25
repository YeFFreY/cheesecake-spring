package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.yeffrey.cheesecakespring.activities.domain.ResourceName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ResourceNameAttributeConverter implements AttributeConverter<ResourceName, String> {
    @Override
    public String convertToDatabaseColumn(ResourceName attribute) {
        return attribute.asString();
    }

    @Override
    public ResourceName convertToEntityAttribute(String dbData) {
        return ResourceName.from(dbData);
    }
}
