package repository.client;

import controller.response.Response;
import model.Client;
import model.builder.ClientBuilder;

import static java.util.Collections.singletonList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + CLIENT;
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                clients.add(getClientFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchClientSql = "Select * from " + CLIENT + " where `id` = " + id;
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            if (clientResultSet.next()) {
                Client client = getClientFromResultSet(clientResultSet);
                return Optional.ofNullable(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("Insert into " + CLIENT + " values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getPersonalNumericalCode());
            insertStatement.setString(3, client.getAddress());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            client.setId(clientId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from " + CLIENT + " where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClientFromResultSet(ResultSet resultSet) throws SQLException {
        return new ClientBuilder()
                .setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setPersonalNumericalCode(resultSet.getString("personal_numerical_code"))
                .setAddress(resultSet.getString("address"))
                .build();
    }

    @Override
    public Response<Boolean> existsByPnc(String personalNumericalCode) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql = "Select * from " + CLIENT + " where personal_numerical_code = \'" +
                    personalNumericalCode + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            return new Response<>(clientResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public boolean update(Long id, String column, String newValue) {
        try {
            Statement statement = connection.createStatement();
            String updateSql = "update " + CLIENT + " set " + column + " = \'" + newValue + "\' where id = " + id;
            statement.executeUpdate(updateSql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
