package com.cacib.pqk.application.partner;

import com.cacib.pqk.partner.Partner;
import com.cacib.pqk.partner.PartnerPersistencePort;
import com.cacib.pqk.partner.PartnerServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PartnerServiceImpl implements PartnerServicePort {

    @Autowired
    public PartnerServiceImpl(PartnerPersistencePort partnerPersistencePort) {
        this.partnerPersistencePort = partnerPersistencePort;
    }

    final PartnerPersistencePort partnerPersistencePort;

    @Override
    public Set<Partner> getAll() {
        return Set.of();
    }

    @Override
    public Partner create(Partner partner) {
        return partner;
    }

    @Override
    public Partner delete(Partner partner) {
        return partner;
    }
}
