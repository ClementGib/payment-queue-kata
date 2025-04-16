package com.cacib.pqk.partner;

import com.cacib.pqk.application.partner.PartnerServiceImpl;
import com.cacib.pqk.partner.type.Direction;
import com.cacib.pqk.partner.type.FlowType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartnerServiceImplTest {

    private PartnerPersistencePort persistencePort;
    private PartnerServiceImpl service;

    @BeforeEach
    void setUp() {
        persistencePort = mock(PartnerPersistencePort.class);
        service = new PartnerServiceImpl(persistencePort);
    }

    private Partner samplePartner() {
        Partner p = new Partner();
        p.setAlias("crm_outbound");
        p.setType("CLIENT");
        p.setDirection(Direction.OUTBOUND);
        p.setFlowType(FlowType.MESSAGE);
        p.setDescription("CRM outbound flow");
        return p;
    }

    @Test
    void shouldReturnAllPartners() {
        Set<Partner> mockSet = Set.of(samplePartner());
        when(persistencePort.getAll()).thenReturn(mockSet);

        Set<Partner> result = service.getAll();

        assertEquals(1, result.size());
        verify(persistencePort).getAll();
    }

    @Test
    void shouldCreatePartner() {
        Partner partner = samplePartner();
        when(persistencePort.create(partner)).thenReturn(Optional.of(partner));

        Optional<Partner> result = service.create(partner);

        assertTrue(result.isPresent());
        assertEquals("crm_outbound", result.get().getAlias());
        verify(persistencePort).create(partner);
    }

    @Test
    void shouldDeletePartner() {
        Partner partner = samplePartner();
        when(persistencePort.deleteByAlias("crm_outbound")).thenReturn(Optional.of(partner));

        Optional<Partner> result = service.delete("crm_outbound");

        assertTrue(result.isPresent());
        assertEquals("crm_outbound", result.get().getAlias());
        verify(persistencePort).deleteByAlias("crm_outbound");
    }
}
