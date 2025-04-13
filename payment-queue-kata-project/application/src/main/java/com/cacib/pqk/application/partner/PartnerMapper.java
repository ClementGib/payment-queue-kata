package com.cacib.pqk.application.partner;

import com.cacib.pqk.partner.Partner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerMapper {
    Partner toDomain(PartnerEntity entity);
    PartnerEntity toEntity(Partner domain);
}
