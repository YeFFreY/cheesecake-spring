package org.yeffrey.cheesecakespring.infrastructure.persistence.converters;

import org.yeffrey.cheesecakespring.library.domain.ResourceDescription;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
class ResourceDescriptionAttributeConverter implements AttributeConverter<ResourceDescription, String> {
    @Override
    public String convertToDatabaseColumn(ResourceDescription attribute) {
        return Objects.nonNull(attribute) ? attribute.asString() : null;
    }

    @Override
    public ResourceDescription convertToEntityAttribute(String dbData) {
        return ResourceDescription.from(dbData);
    }
}
