/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaPadre implements Serializable {

    protected String empresa;
    private static final Map<String, EntityManagerFactory> emfs = new HashMap<>();

    public JpaPadre(String empresa) {
        this.empresa = empresa;
        // Verificar si ya existe un EntityManagerFactory para esta empresa
        if (!emfs.containsKey(empresa)) {
            switch (empresa) {
                case "a":
                    emfs.put(empresa, Persistence.createEntityManagerFactory("com.mycompany_tutoria_war_1.0-SNAPSHOTPU"));
                    break;
                default:
                    throw new IllegalArgumentException("NO INICIO SESION CORRECTAMENTE");
            }
        }
    }

    public EntityManager getEntityManager() {
        // Recuperar el EntityManagerFactory desde el Map y crear un EntityManager
        EntityManagerFactory emf = emfs.get(empresa);
        if (emf == null) {
            throw new IllegalStateException("NO INICIO SESION CORRECTAMENTE");
        }
        return emf.createEntityManager();
    }
}
