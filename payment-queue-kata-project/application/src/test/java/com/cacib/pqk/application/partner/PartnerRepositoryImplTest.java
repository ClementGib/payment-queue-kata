package com.cacib.pqk.application.partner;

import com.cacib.pqk.application.partner.repository.PartnerJpaRepository;
import com.cacib.pqk.partner.*;
import com.cacib.pqk.partner.type.Direction;
import com.cacib.pqk.partner.type.FlowType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartnerRepositoryImplTest {

    private PartnerJpaRepository jpaRepository;
    private PartnerMapper partnerMapper;
    private PartnerRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(PartnerJpaRepository.class);
        partnerMapper = mock(PartnerMapper.class);
        repository = new PartnerRepositoryImpl(jpaRepository, partnerMapper);
    }

    private Partner samplePartner() {
        Partner partner = new Partner();
        partner.setAlias("crm_outbound");
        partner.setType("CLIENT");
        partner.setDirection(Direction.OUTBOUND);
        partner.setFlowType(FlowType.MESSAGE);
        partner.setDescription("CRM outbound");
        return partner;
    }

    private PartnerEntity sampleEntity() {
        PartnerEntity entity = new PartnerEntity();
        entity.setAlias("crm_outbound");
        entity.setType("CLIENT");
        entity.setDirection(Direction.OUTBOUND);
        entity.setFlowType(FlowType.MESSAGE);
        entity.setDescription("CRM outbound");
        return entity;
    }

    @Test
    void shouldReturnAllPartners() {
        PartnerEntity entity = sampleEntity();
        Partner domain = samplePartner();

        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(partnerMapper.toDomain(entity)).thenReturn(domain);

        Set<Partner> result = repository.getAll();

        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(p -> "crm_outbound".equals(p.getAlias())));
        verify(jpaRepository).findAll();
        verify(partnerMapper).toDomain(entity);
    }

    @Test
    void shouldCreatePartnerWhenNotExists() {
        Partner partner = samplePartner();
        PartnerEntity entity = sampleEntity();

        when(jpaRepository.existsById("crm_outbound")).thenReturn(false);
        when(partnerMapper.toEntity(partner)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(partnerMapper.toDomain(entity)).thenReturn(partner);

        Optional<Partner> result = repository.create(partner);

        assertTrue(result.isPresent());
        assertEquals("crm_outbound", result.get().getAlias());
        verify(jpaRepository).save(entity);
    }

    @Test
    void shouldNotCreatePartnerIfExists() {
        Partner partner = samplePartner();
        when(jpaRepository.existsById("crm_outbound")).thenReturn(true);

        Optional<Partner> result = repository.create(partner);

        assertTrue(result.isEmpty());
        verify(jpaRepository, never()).save(any());
    }

    @Test
    void shouldDeletePartnerIfFound() {
        PartnerEntity entity = sampleEntity();
        Partner partner = samplePartner();

        when(jpaRepository.findById("crm_outbound")).thenReturn(Optional.of(entity));
        when(partnerMapper.toDomain(entity)).thenReturn(partner);

        Optional<Partner> result = repository.deleteByAlias("crm_outbound");

        assertTrue(result.isPresent());
        assertEquals("crm_outbound", result.get().getAlias());
        verify(jpaRepository).delete(entity);
    }

    @Test
    void shouldNotDeletePartnerIfNotFound() {
        when(jpaRepository.findById("unknown")).thenReturn(Optional.empty());

        Optional<Partner> result = repository.deleteByAlias("unknown");

        assertTrue(result.isEmpty());
        verify(jpaRepository, never()).delete(any());
    }
}
