package com.manning.javapersistence.ch02;


import com.manning.javapersistence.ch02.entity.Message;
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

            Message message = new Message();
            message.setText("Hello World!");

            em.persist(message);
            em.getTransaction().commit();

            em.getTransaction().begin();
            List<Message> messages = em.createQuery("SELECT m FROM Message m").getResultList();
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
