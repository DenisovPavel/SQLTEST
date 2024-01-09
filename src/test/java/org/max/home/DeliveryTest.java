package org.max.home;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DeliveryTest extends AbstractTest {

    @Test
    //@Order(1)
    @DisplayName("1.Тест на количество элементов, где указана дата прибытия товара, но товар не выдан! ")
    void getElementWithDateArrivalNotTaken() throws SQLException {
        //given
        String sql = "SELECT date_arrived FROM delivery WHERE taken = 'NO' ";
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;

        }
        //then
        Assertions.assertEquals(2, countTableSize);
    }

    @Test
   // @Order(2)
    @DisplayName("2.Тест на количество элементов, где ID курьера = 0 ")
    void getSizeOfTableDelivery() throws SQLException {
        //given
        String sql = "SELECT delivery_id  FROM delivery WHERE courier_id = " + 0;
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;
            String element = rs.toString();
            System.out.println(element);
        }
        //then
        Assertions.assertEquals(0, countTableSize);
    }

    @Test
   // @Order(3)
    @DisplayName("3. Тест на проверку количества элементов через Hibernate Query ")
    void getSizeElementsThrowHibQuery() throws SQLException {
        //Given
        //When
        final Query query = getSession().createSQLQuery("SELECT * FROM delivery").
                addEntity(DeliveryEntity.class);

        //Then
        Assertions.assertEquals(15, query.list().size());
    }


    @Test
    @Disabled
    //@Order(4)
    @DisplayName("4. Тест на проверку оплаты для только что добавленного элемента.")
    void getNewElementOfTableDelivery() {
        //Given
        DeliveryEntity entity = new DeliveryEntity();
        entity.setDeliveryId((short) 16);

        entity.setDateArrived("2024-01-09 21:00:29");
        entity.setTaken("Yes");
        entity.setPaymentMethod("Cash");
        //When
        Session session = getSession();// mk session
        session.beginTransaction(); // start TR
        session.persist(entity); // add entity to session
        session.getTransaction().commit(); // save session

        final Query query = getSession()
                .createSQLQuery("SELECT * FROM delivery WHERE delivery_id =" + 16)
                .addEntity(DeliveryEntity.class);
        DeliveryEntity deliveryEntity = (DeliveryEntity) query.uniqueResult();
        //Then
        //Check that element exist
        Assertions.assertNotNull(deliveryEntity);
        //Check payment_method
        Assertions.assertEquals("Cash", deliveryEntity.getPaymentMethod());
    }


}

