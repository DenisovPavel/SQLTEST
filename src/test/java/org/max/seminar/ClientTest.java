package org.max.seminar;

import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.max.demo.EmployeeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientTest extends AbstractTest {

    @Test
    @DisplayName("Проверка количества элементов в таблице")
    void getClients_whenValid_shouldReturn() throws SQLException {
        //given
        String sql = "SELECT * FROM client"; // запрос в бд
        Statement stmt  = getConnection().createStatement(); // создаем некое заявление обращение с подключением к бд
        int countTableSize = 0; // определяем счетчик для подчета элементов
        //when
        ResultSet rs = stmt.executeQuery(sql);//ResultSet представляет результирующий набор
                                          // данных и обеспечивает запрос к бд через executeQuery(sql)
        while (rs.next()) { // чекаем на элементы
            countTableSize++;
        }
        final Query query = getSession().createSQLQuery(sql).addEntity(ClientEntity.class);// создали сессию где
        // прокинули запрос createSQLQuery(sql) создавая сущность класса addEntity(ClientEntity.class) смапили класс

        //then
        Assertions.assertEquals(3, countTableSize);
      //  Assertions.assertEquals(3, query.list().size());
    }

    @ParameterizedTest
    @CsvSource({"1, Иванов", "2, Петров", "3, Сидоров"})
    void getClientById_whenValid_shouldReturn(int id, String lastName) throws SQLException {
        //given
        String sql = "SELECT * FROM client WHERE client_id=" + id;
        Statement stmt  = getConnection().createStatement();
        String name = "";
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            name = rs.getString(3);
        }
        //then
        Assertions.assertEquals(lastName, name);
    }
}
