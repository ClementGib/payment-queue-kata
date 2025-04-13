package com.cacib.pqk.application.partner;

import com.cacib.pqk.application.partner.repository.PartnerJpaRepository;
import com.cacib.pqk.partner.Partner;
import com.cacib.pqk.partner.PartnerPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PartnerRepositoryImpl implements PartnerPersistencePort {

    private final PartnerJpaRepository partnerJpaRepository;
    private final PartnerMapper partnerMapper;

    @Autowired
    public PartnerRepositoryImpl(PartnerJpaRepository partnerJpaRepository, PartnerMapper partnerMapper) {
        this.partnerJpaRepository = partnerJpaRepository;
        this.partnerMapper = partnerMapper;
    }

    @Override
    public Set<Partner> getAll() {
        return partnerJpaRepository.findAll().stream()
                .map(partnerMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Partner create(Partner partner) {
        return null;
    }

    @Override
    public Optional<Partner> deleteByAlias(String alias) {
        return Optional.empty();
    }
}
