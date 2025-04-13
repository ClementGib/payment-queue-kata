package com.cacib.pqk.application.partner;

import com.cacib.pqk.partner.Partner;
import com.cacib.pqk.partner.PartnerPersistencePort;
import com.cacib.pqk.partner.PartnerServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        return partnerPersistencePort.getAll();
    }

    @Override
    public Optional<Partner> create(Partner newPartner) {
        return partnerPersistencePort.create(newPartner);
    }

    @Override
    public Optional<Partner> delete(String alias) {
        return partnerPersistencePort.deleteByAlias(alias);
    }
}
