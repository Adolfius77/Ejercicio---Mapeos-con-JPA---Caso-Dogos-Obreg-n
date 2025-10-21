/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author adoil
 */
public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "dogosPU";
    private static EntityManagerFactory em;

    public JpaUtil() {
    }
    
    public static  EntityManagerFactory getEntityManagerFactory(){
        if(em == null){
            synchronized (JpaUtil.class) {
                if(em == null){
                    em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                }
            }
        }
        return em;
    }
    public static EntityManager createEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
    public static void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

}
