package com.cacib.pqk.domain.partner;

import java.util.Optional;
import java.util.Set;

public interface PartnerServicePort {
    /**
     * find all partners
     *
     * @return Set with all Partners
     */
    Set<Partner> getAll();

    /**
     * Create new Partner
     *
     * @param newPartner to create
     * @return optional created Partner
     */
    Optional<Partner> create(Partner newPartner);

    /**
     * Delete existing Partner
     *
     * @param partner alias to delete
     * @return optional deleted Partner
     */
    Optional<Partner> delete(String partner);
}
