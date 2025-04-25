package com.cacib.pqk.domain.partner;

import java.util.Optional;
import java.util.Set;

public interface PartnerPersistencePort {

    /**
     * find every Partners
     * @return all Partners
     */
    public Set<Partner> getAll();

    /**
     * find Partners with matching alias
     * @return specific Partner
     */
    public Optional<Partner> getByAlias(String alias);
    
    /**
     * create the current Partner
     *
     * @param partner to create
     * @return created Partner
     */
    public Optional<Partner> create(Partner partner);

    /**
     * delete Partner from its alias
     * 
     * @param alias of Partner
     * @return deleted Partner if alias found and Partner was deleted
     */
    public Optional<Partner> deleteByAlias(String alias);
}
