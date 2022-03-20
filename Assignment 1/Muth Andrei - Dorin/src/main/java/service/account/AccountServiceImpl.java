package service.account;

import model.Account;
import model.Event;
import model.builder.EventBuilder;
import repository.account.AccountRepository;
import repository.event.EventRepository;
import session.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static database.Constants.Rights.*;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final Session session;
    private final EventRepository eventRepository;

    public AccountServiceImpl(AccountRepository accountRepository, Session session, EventRepository eventRepository) {
        this.accountRepository = accountRepository;
        this.session = session;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public boolean save(Account account) {
        eventRepository.save(createEvent(CREATE_ACCOUNT));
        return accountRepository.save(account);
    }

    @Override
    public boolean updateStringFields(Long id, String cardNumber, String type) {
        eventRepository.save(createEvent(UPDATE_ACCOUNT));
        if(!cardNumber.isBlank()) {
            if(!accountRepository.updateStringOrDateField(id, "card_number", cardNumber)) {
                return false;
            }
        }
        return accountRepository.updateStringOrDateField(id, "type", type);
    }

    @Override
    public boolean updateLongFields(Long id, String clientId, String amountOfMoney) {
        if(!clientId.isEmpty()) {
            if(!accountRepository.updateLongField(id, "client_id", Long.parseLong(clientId))) {
                return false;
            }
        }
        if(!amountOfMoney.isBlank()) {
            return accountRepository.updateLongField(id, "amount_of_money", Long.parseLong(amountOfMoney));
        }
        return true;
    }

    @Override
    public boolean updateDateField(Long id, Date newDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return accountRepository.updateStringOrDateField(id, "date_of_creation", dateFormat.format(newDate));
    }

    @Override
    public boolean transferBetweenAccounts(Long from, Long to, Long sum) {
        eventRepository.save(createEvent(TRANSFER_MONEY));
        Account fromAccount = accountRepository.findById(from).get();
        Account toAccount = accountRepository.findById(to).get();

        Long fromNew = fromAccount.getAmountOfMoney() - sum;
        Long toNew = toAccount.getAmountOfMoney() + sum;

        return accountRepository.updateLongField(from, "amount_of_money", fromNew) &&
                accountRepository.updateLongField(to, "amount_of_money", toNew);

    }

    @Override
    public boolean remove(Long id) {
        eventRepository.save(createEvent(DELETE_ACCOUNT));
        return accountRepository.delete(id);
    }

    @Override
    public boolean processBill(Long id, Long cost) {
        eventRepository.save(createEvent(PROCESS_BILLS));
        Account account = accountRepository.findById(id).get();
        Long newSum = account.getAmountOfMoney() - cost;

        return accountRepository.updateLongField(id, "amount_of_money", newSum);
    }

    private Event createEvent(String action) {
        return new EventBuilder()
                .setUserId(session.getLoggedUser().get().getId())
                .setAction(action)
                .setDate(new Date())
                .build();
    }

    @Override
    public void removeAll() {
        accountRepository.removeAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }
}
