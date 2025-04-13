package com.cacib.pqk.application.partner.repository;

import com.cacib.pqk.application.partner.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerJpaRepository extends JpaRepository<PartnerEntity, String> {
}
