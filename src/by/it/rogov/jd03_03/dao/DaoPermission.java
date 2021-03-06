package by.it.rogov.jd03_03.dao;

import by.it.rogov.jd03_03.beans.Permission;
import by.it.rogov.jd03_03.connection.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DaoPermission extends AbstractDao implements InterfaceDAO<Permission> {

    @Override
    public Permission read(long id) throws SQLException {
        List<Permission> all = getAll("where id=" + id);
        if (all.size() > 0)
            return all.get(0);
        else
            return null;
    }

    @Override
    public boolean create(Permission permission) throws SQLException {
        String sql = String.format(Locale.US,"INSERT INTO `permission`( `pemissionAccess`, `permissionUpdate`, `users_ID`, `labrary_ID`) " +
                        "VALUES (%d,%d,%d,%d)",
                permission.isPemissionAccess(),permission.isPermissionUpdate(),permission.getUsers_id(),permission.getLibrary_id());
        long id = executeUpdate(sql);
        if (id > 0) {
            permission.setId(id);
            return true;
        } else
            return false;
    }

    @Override
    public boolean update(Permission permission) throws SQLException {
        String sql = String.format(Locale.US,
                "UPDATE `permission` SET `pemissionAccess`=%d,`permissionUpdate`=%d,`users_ID`=%d,`labrary_ID`=%d " +
                        "WHERE id=%d",
                permission.isPemissionAccess(),permission.isPermissionUpdate(),permission.getUsers_id(),permission.getLibrary_id(),permission.getId());
        return executeUpdate(sql) > 0;
    }

    @Override
    public boolean delete(Permission permission) throws SQLException {
        String sql = String.format(Locale.US,
                "DELETE FROM `permission` WHERE id=%d",
                permission.getId());
        return executeUpdate(sql) > 0;
    }

    @Override
    public List<Permission> getAll(String whereAndOrder) throws SQLException {
        List<Permission> permissions = new ArrayList<>();
        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement()
        ) {
            String sql = String.format(Locale.US,"SELECT `ID`, `pemissionAccess`, `permissionUpdate`, `users_ID`, `labrary_ID` FROM `permission` %s",
                    whereAndOrder);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Permission permission = new Permission(
                        resultSet.getLong("id"),
                        resultSet.getInt("pemissionAccess"),
                        resultSet.getInt("permissionUpdate"),
                        resultSet.getLong("users_ID"),
                        resultSet.getLong("labrary_ID")
                );
                permissions.add(permission);
            }
        }
        return permissions;

    }
}
