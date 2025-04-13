package com.cacib.pqk.partner;

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
     * @param partner to create
     * @return created Partner
     */
    Partner create(Partner partner);

    /**
     * Delete existing Partner
     *
     * @param partner to delete
     * @return deleted Partner
     */
    Partner delete(Partner partner);
}
