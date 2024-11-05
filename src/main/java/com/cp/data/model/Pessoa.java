


package com.cp.data.model;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;

import static jakarta.persistence.CascadeType.ALL;

/**
 *
 * @author utfpr
 */
@Entity
@Data
public class Pessoa implements Serializable {

    public Pessoa(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;
    private String nome;


    @ManyToOne(fetch = FetchType.LAZY,cascade = ALL, optional = true)
    @JoinColumn(name = "cidade")
  //  @JsonManagedReference
    private Cidade cidade;
}
