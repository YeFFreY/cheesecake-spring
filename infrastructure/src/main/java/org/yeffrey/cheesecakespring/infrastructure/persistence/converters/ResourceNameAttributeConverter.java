package org.yeffrey.cheesecakespring.infrastructure.persistence.converters;

import org.yeffrey.cheesecakespring.library.domain.ResourceName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
class ResourceNameAttributeConverter implements AttributeConverter<ResourceName, String> {
    @Override
    public String convertToDatabaseColumn(ResourceName attribute) {
        return Objects.nonNull(attribute) ? attribute.asString() : null;
    }

    @Override
    public ResourceName convertToEntityAttribute(String dbData) {
        return ResourceName.from(dbData);
    }
}
