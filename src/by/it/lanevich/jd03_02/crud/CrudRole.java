package by.it.lanevich.jd03_02.crud;

import by.it.lanevich.jd03_02.beans.Role;
import by.it.lanevich.jd03_02.connection.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class CrudRole {

    public boolean create(Role role) throws SQLException {
        try (
                Connection connection = DbConnection.getConnection();
                Statement statement = connection.createStatement()
        ) {
            String sql = String.format(Locale.US,
                    "INSERT INTO " +
                            "`roles`(`role`) " +
                            "VALUES ('%s')",
                    role.getRole());
            if (1 == statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS)) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getLong(1));
                    return true;
                }
            }
        }
        return false;
    }

    public Role read(long id) throws SQLException {
        Role user = null;
        try (
                Connection connection = DbConnection.getConnection();
                Statement statement = connection.createStatement()
        ) {
            String sql = String.format(Locale.US, "" +
                            "SELECT `id`, `role` FROM `role` WHERE id=%d",
                    id);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                user = new Role(
                        resultSet.getLong("id"),
                        resultSet.getString("role")
                );
            }
        }

        return user;
    }

    public boolean update(Role role) throws SQLException {
        try (
                Connection connection = DbConnection.getConnection();
                Statement statement = connection.createStatement()
        ) {
            String sql = String.format(Locale.US,
                    "UPDATE `roles` " +
                            "SET `role`='%s' WHERE id=%d",
                    role.getRole(), role.getId());
            return 1 == statement.executeUpdate(sql);
        }
    }


    public boolean delete(Role role) throws SQLException {
        try (
                Connection connection = DbConnection.getConnection();
                Statement statement = connection.createStatement()
        ) {
            String sql = String.format(Locale.US,
                    "DELETE FROM `roles` WHERE id=%d",
                    role.getId());
            return 1 == statement.executeUpdate(sql);
        }
    }
    public static int findRoleId(String role) throws SQLException {
        String sql = String.format("SELECT * FROM `roles` WHERE `role`='%s'", role);
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        return -1;

}
}
