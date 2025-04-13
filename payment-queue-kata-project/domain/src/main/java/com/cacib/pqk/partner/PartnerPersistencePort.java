package com.cacib.pqk.partner;

import java.util.Optional;
import java.util.Set;

public interface PartnerPersistencePort {

    /**
     * find every Partners
     * @return all Partners
     */
    public Set<Partner> getAll();
    
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
