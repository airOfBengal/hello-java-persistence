package com.manning.javapersistence.ch02;


import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HelloWorldJpaTest {

    @Test
    public void storeLoadMessage(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch02");
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Messsage messsage = new Messsage();
            messsage.setText("Hello World!");

            em.persist(messsage);
            em.getTransaction().commit();

            em.getTransaction().begin();
            List<Messsage> messages = em.createQuery("SELECT m FROM Messsage m").getResultList();
            messages.get(messages.size()-1).setText("Hello World from JPA!");

            em.getTransaction().commit();

            assertAll(
                    () -> assertEquals(1, messages.size()),
                    () -> assertEquals("Hello World from JPA!", messages.get(0).getText())
            );

            em.close();
        } finally {
            emf.close();
        }

    }
}
