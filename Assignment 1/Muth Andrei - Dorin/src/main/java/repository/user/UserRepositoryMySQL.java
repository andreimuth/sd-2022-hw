package repository.user;

import controller.response.Response;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.*;

import static database.Constants.Tables.*;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + USER;
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `email`=\'" + email + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            User user = getUserFromResultSet(userResultSet);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO " + USER + " values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getEmail());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + USER + " where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByUsername(String email) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `email`=\'" + email + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public List<User> findByRole(Role role) {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "select * from "+ USER + " join " + USER_ROLE + " ur on " + USER +".id = ur.user_id join " + ROLE + " r " +
                    "on r.id = ur.role_id where role = \'" + role.getRole() + "\'";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while(resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(resultSet.getLong("id")))
                .build();
    }

    @Override
    public Optional<User> findById(Long id) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchUserSql = "Select * from " + USER + " where `id` = " + id;
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = getUserFromResultSet(userResultSet);
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByIdAndRole(Long id, Role role) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchUserSql = "select * from "+ USER + " join " + USER_ROLE + " ur on " + USER +".id = ur.user_id join " + ROLE + " r " +
                    "on r.id = ur.role_id where role = \'" + role.getRole() + "\' and " + USER + ".id = " + id;
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = getUserFromResultSet(userResultSet);
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Long id, String column, String newValue) {
        try {
            Statement statement = connection.createStatement();
            String updateSql = "update " + USER + " set " + column + " = \'" + newValue + "\' where id = " + id;
            statement.executeUpdate(updateSql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sqlDelete = "delete from " + USER + " where id = " + id;
            statement.executeUpdate(sqlDelete);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
