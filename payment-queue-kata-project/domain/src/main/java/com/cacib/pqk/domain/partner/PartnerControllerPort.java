package com.cacib.pqk.domain.partner;

import java.util.Optional;
import java.util.Set;

public interface PartnerControllerPort {

    /**
     * Find all Partners
     *
     * @return all Partners found
     */
    Set<Partner> getAll();

    /**
     * Create a new partner
     *
     * @param newPartner to create
     * @return Optional Partner if created or not
     */
    Optional<Partner> create(Partner newPartner);

    /**
     * Delete an existing partner
     *
     * @param existingPartnerAlias to delete
     * @return Optional Partner if deleted or not
     */
    Optional<Partner> delete(String existingPartnerAlias);
}
