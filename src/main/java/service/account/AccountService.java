package service.account;

import model.Account;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAll();

    boolean save(Account account);

    boolean updateStringFields(Long id, String cardNumber, String type);

    boolean updateLongFields(Long id, String clientId, String amountOfMoney);

    boolean updateDateField(Long id, Date newDate);

    boolean transferBetweenAccounts(Long from, Long to, Long sum);

    boolean remove(Long id);

    boolean processBill(Long id, Long cost);

    void removeAll();

    Optional<Account> findById(Long id);

}
