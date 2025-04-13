package com.cacib.pqk.client;

import com.cacib.pqk.partner.*;
import com.cacib.pqk.partner.type.Direction;
import com.cacib.pqk.partner.type.FlowType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartnerControllerTest {

    private PartnerServicePort partnerServicePort;
    private PartnerController controller;

    @BeforeEach
    void setUp() {
        partnerServicePort = Mockito.mock(PartnerServicePort.class);
        controller = new PartnerController(partnerServicePort);
    }

    private Partner samplePartner() {
        Partner partner = new Partner();
        partner.setAlias("crm_outbound");
        partner.setType("CLIENT");
        partner.setDirection(Direction.OUTBOUND);
        partner.setFlowType(FlowType.MESSAGE);
        partner.setDescription("CRM outbound flow");
        return partner;
    }

    @Test
    void shouldReturnAllPartners() {
        Set<Partner> partners = Set.of(samplePartner());
        when(partnerServicePort.getAll()).thenReturn(partners);

        Set<Partner> result = controller.getAll();

        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(p -> "crm_outbound".equals(p.getAlias())));
    }

    @Test
    void shouldCreatePartner() {
        Partner input = samplePartner();
        when(partnerServicePort.create(input)).thenReturn(Optional.of(input));

        Optional<Partner> result = controller.create(input);

        assertTrue(result.isPresent());
        assertEquals("crm_outbound", result.get().getAlias());
    }

    @Test
    void shouldDeletePartner() {
        when(partnerServicePort.delete("crm_outbound")).thenReturn(Optional.of(samplePartner()));

        Optional<Partner> result = controller.delete("crm_outbound");

        assertTrue(result.isPresent());
        assertEquals("crm_outbound", result.get().getAlias());
    }
}
