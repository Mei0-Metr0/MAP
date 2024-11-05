
package com.cp.web;

import com.cp.data.crud.BeanCrudPessoa;
import com.cp.data.crud.BeanCrudCidade;
import com.cp.data.model.Pessoa;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author utfpr
 */
@Named("beanJFPessoa")
@RequestScoped
public class BeanJFPessoa {
    
    @Getter @Setter
    private int id;
    
    @Getter @Setter
    private String nome;

    @Getter @Setter
    private int cidade;
    
    @EJB 
    BeanCrudPessoa beanPessoa;

    @EJB
    BeanCrudCidade beanCidade;
    
    public void add(){
        Pessoa p = new Pessoa();
        p.setNome(nome);
        p.setId(id);
        beanPessoa.persist(p);
        p.setCidade(beanCidade.find(cidade));
        beanPessoa.merge(p);
    }
    
    public List<Pessoa> getAll(){
        return beanPessoa.getAll();
    }
}
