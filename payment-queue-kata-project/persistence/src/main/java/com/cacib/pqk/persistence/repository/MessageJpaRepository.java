package com.cacib.pqk.persistence.repository;

import com.cacib.pqk.persistence.message.MessageEntity;
import com.cacib.pqk.persistence.partner.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<MessageEntity, String> {
}
