
package com.cp.data.crud;

import com.cp.data.crud.interfaces.EMNames;
import com.cp.data.crud.interfaces.AbstractCrud;
import com.cp.data.model.Pessoa;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 *
 * @author utfpr
 */
@Stateless
public class BeanCrudPessoa extends AbstractCrud<Pessoa> {

    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        if (em == null) {
            em = Persistence.createEntityManagerFactory(EMNames.EMN1, EMNames.getEMN1Props()).createEntityManager();
        }
        return em;
    }
    
    public BeanCrudPessoa() {
        super(Pessoa.class);
    }


}
