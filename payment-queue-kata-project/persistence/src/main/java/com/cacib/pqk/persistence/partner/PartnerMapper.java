package com.cacib.pqk.persistence.partner;

import com.cacib.pqk.persistence.partner.json.JsonConverter;
import com.cacib.pqk.partner.Partner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface PartnerMapper {
    Partner toDomain(PartnerEntity entity);
    PartnerEntity toEntity(Partner domain);
}
