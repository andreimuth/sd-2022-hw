package repository.account;

import controller.response.Response;
import model.Account;
import model.Client;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> findAll();

    Optional<Account> findById(Long id);

    Response<Boolean> existsByCardNumber(String cardNumber);

    boolean save(Account account);

    boolean updateStringOrDateField(Long id, String column, String newValue);

    boolean updateLongField(Long id, String column, Long newValue);

    boolean delete(Long id);

    void removeAll();

}
