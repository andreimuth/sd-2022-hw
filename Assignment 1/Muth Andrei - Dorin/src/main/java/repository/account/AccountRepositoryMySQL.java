package repository.account;

import controller.response.Response;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.CLIENT;
import static java.util.Collections.singletonList;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + ACCOUNT;
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                accounts.add(getAccountFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Optional<Account> findById(Long id) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchClientSql = "Select * from " + ACCOUNT + " where `id` = " + id;
            ResultSet accountResultSet = statement.executeQuery(fetchClientSql);
            if (accountResultSet.next()) {
                Account account = getAccountFromResultSet(accountResultSet);
                return Optional.ofNullable(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
        return new AccountBuilder()
                .setId(resultSet.getLong("id"))
                .setCardNumber(resultSet.getString("card_number"))
                .setType(resultSet.getString("type"))
                .setClientId(resultSet.getLong("client_id"))
                .setAmountOfMoney(resultSet.getLong("amount_of_money"))
                .setDateOfCreation(new Date(resultSet.getDate("date_of_creation").getTime()))
                .build();
    }

    @Override
    public Response<Boolean> existsByCardNumber(String cardNumber) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql = "Select * from " + ACCOUNT + " where card_number = \'" +
                    cardNumber + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchClientSql);
            return new Response<>(accountResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("Insert into " + ACCOUNT + " values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, account.getCardNumber());
            insertStatement.setLong(2, account.getClientId());
            insertStatement.setString(3, account.getType());
            insertStatement.setLong(4, account.getAmountOfMoney());
            insertStatement.setDate(5, new Date(account.getDateOfCreation().getTime()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            rs.next();
            long accountId = rs.getLong(1);
            account.setId(accountId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStringOrDateField(Long id, String column, String newValue) {
        try {
            Statement statement = connection.createStatement();
            String updateSql = "update " + ACCOUNT + " set " + column + " = \'" + newValue + "\' where id = " + id;
            statement.executeUpdate(updateSql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateLongField(Long id, String column, Long newValue) {
        try {
            Statement statement = connection.createStatement();
            String updateSql = "update " + ACCOUNT + " set " + column + " = " + newValue + " where id = " + id;
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
            String deleteSql = "delete from " + ACCOUNT + " where id = " + id;
            statement.executeUpdate(deleteSql);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from " + ACCOUNT + " where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
