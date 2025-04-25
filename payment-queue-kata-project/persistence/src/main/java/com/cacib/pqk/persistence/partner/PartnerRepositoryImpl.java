package com.cacib.pqk.persistence.partner;

import com.cacib.pqk.persistence.repository.PartnerJpaRepository;
import com.cacib.pqk.domain.partner.Partner;
import com.cacib.pqk.domain.partner.PartnerPersistencePort;
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
    public Optional<Partner> getByAlias(String alias) {
        return partnerJpaRepository.findById(alias).stream()
                .map(partnerMapper::toDomain).findFirst();
    }

    @Override
    public Optional<Partner> create(Partner partner) {
        if (partnerJpaRepository.existsById(partner.getAlias())) {
            return Optional.empty();
        }

        PartnerEntity entity = partnerMapper.toEntity(partner);
        PartnerEntity save = partnerJpaRepository.save(entity);
        return Optional.of(partnerMapper.toDomain(save));
    }

    @Override
    public Optional<Partner> deleteByAlias(String alias) {
        Optional<PartnerEntity> entity = partnerJpaRepository.findById(alias);
        if (entity.isPresent()) {
            PartnerEntity partnerEntity = entity.get();
            partnerJpaRepository.delete(partnerEntity);
            return Optional.of(partnerMapper.toDomain(partnerEntity));
        }
        return Optional.empty();
    }
}