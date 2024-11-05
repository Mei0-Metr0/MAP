/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cp.data.crud.interfaces;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author utfpr
 */
public class EMNames implements Serializable {

    public static final String EMN1 = "default";

    public static Map<String, String> getEMN1Props() {

        String BD_producao = System.getenv("DATABASE_URL"); // variavel de ambiente criada pelo Heroku
        Map<String, String> properties = new HashMap<>();

        if (BD_producao == null) { //caso nao tenhamos a variavel de ambiente       
            properties.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/localdb");
            properties.put("jakarta.persistence.jdbc.user", "postgres");
            properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
            properties.put("jakarta.persistence.jdbc.password", "postgres");
        } else { //se a variavel de ambiente foi criada, indica que o projeto está alocado em producao
            String jdbc_database_username = System.getenv("DATABASE_USERNAME");
            String jdbc_database_password = System.getenv("DATABASE_PASSWORD");
            properties.put("jakarta.persistence.jdbc.url", BD_producao );
            properties.put("jakarta.persistence.jdbc.user", jdbc_database_username);
            properties.put("jakarta.persistence.jdbc.password", jdbc_database_password);
            properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        }
        return properties;
    }
}
