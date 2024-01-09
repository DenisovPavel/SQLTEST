package org.max.home;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourierTest extends AbstractTest {

    @Test
    @DisplayName("Test 1- Check Number of Couriers")
    void getSize_Of_Couriers() throws SQLException {
        //Given

        String sql = "SELECT * FROM courier_info";
        Statement statement = getConnection().createStatement();
        int sizeOfTable = 0;
        //When
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            sizeOfTable++;
            System.out.println(sizeOfTable);
        }
        Assertions.assertEquals(4, sizeOfTable);
        //Then
    }

    @Test
    @DisplayName("Test 2- Check Number of Couriers where delivery_type -> NotFoot")
    void getSize_Of_CouriersWhereDeliveryTypeNotCar() throws SQLException {
        //Given
        String sql = "SELECT * FROM courier_info WHERE delivery_type = 'foot'";
        Statement statement = getConnection().createStatement();
        int sizeOfTable = 0;
        //When
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            sizeOfTable++;
            System.out.println(sizeOfTable);
        }
        Assertions.assertEquals(1, sizeOfTable);
        //Then
    }
    @Test
    @DisplayName("Test 3- Check Number of Couriers ThrowHibQuery")
    void getSize_Of_CouriersThrowHibQuery() throws SQLException {
        //Given
        String sql = "SELECT * FROM courier_info";
        //When
        final Query query = (Query) getSession()
                .createSQLQuery(sql)
                .addEntity(CourierInfoEntity.class);
        //Then
        Assertions.assertEquals(4, query.list().size());
    }

    @Test
    @DisplayName("Test 4- Check Number of Couriers ThrowHibQuery DeliveryTypeCar")
    void getSize_Of_CouriersThrowHibQueryDeliveryTypeCar() throws SQLException {
        //Given
        String sql = "SELECT * FROM courier_info WHERE delivery_type='car'";
        //When
        final Query query = (Query) getSession()
                .createSQLQuery(sql)
                .addEntity(CourierInfoEntity.class);
        System.out.println(query.list().size());
        //Then
        Assertions.assertEquals(3, query.list().size());
    }

    @ParameterizedTest
    @Disabled
    @CsvSource({"1, John", "2, Kate"})
// @DisplayName("")
    void getSize_Of_CouriersWhereDeliveryTypeNotCar(int courier_id, String first_name) throws SQLException {
        //Given
        String sql = "SELECT * FROM courier_info WHERE first_name = " + first_name;
        Statement stmt = getConnection().createStatement();
        String name = "";
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            name = rs.getString(2);
        }
        //Then
        Assertions.assertEquals(first_name, name);

    }
}
