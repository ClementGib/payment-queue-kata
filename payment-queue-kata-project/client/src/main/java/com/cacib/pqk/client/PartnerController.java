package com.cacib.pqk.client;

import com.cacib.pqk.partner.Partner;
import com.cacib.pqk.partner.PartnerControllerPort;
import com.cacib.pqk.partner.PartnerServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/partners")
public class PartnerController implements PartnerControllerPort {

    @Autowired
    public PartnerController(PartnerServicePort partnerServicePort) {
        this.partnerServicePort = partnerServicePort;
    }

    final PartnerServicePort partnerServicePort;

    @Override
    @GetMapping
    public Set<Partner> getAll() {
        return partnerServicePort.getAll();
    }

    @Override
    @PostMapping
    public Optional<Partner> create(@Valid @RequestBody Partner newPartner) {
        return partnerServicePort.create(newPartner);
    }

    @Override
    @DeleteMapping("/{alias}")
    public Optional<Partner> delete(@Valid @PathVariable("alias") String alias) {
        return partnerServicePort.delete(alias);
    }
}