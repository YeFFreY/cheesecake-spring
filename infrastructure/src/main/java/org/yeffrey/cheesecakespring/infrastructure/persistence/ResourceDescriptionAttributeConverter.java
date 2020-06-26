package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.yeffrey.cheesecakespring.activities.domain.ResourceDescription;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class ResourceDescriptionAttributeConverter implements AttributeConverter<ResourceDescription, String> {
    @Override
    public String convertToDatabaseColumn(ResourceDescription attribute) {
        return Objects.nonNull(attribute) ? attribute.asString() : null;
    }

    @Override
    public ResourceDescription convertToEntityAttribute(String dbData) {
        return ResourceDescription.from(dbData);
    }
}
