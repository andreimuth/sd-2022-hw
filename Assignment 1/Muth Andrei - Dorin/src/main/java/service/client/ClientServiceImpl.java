package service.client;

import model.Client;
import model.Event;
import model.builder.ClientBuilder;
import model.builder.EventBuilder;
import repository.client.ClientRepository;
import repository.event.EventRepository;
import session.Session;

import static database.Constants.Rights.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final Session session;
    private final EventRepository eventRepository;

    public ClientServiceImpl(ClientRepository clientRepository, Session session, EventRepository eventRepository) {
        this.clientRepository = clientRepository;
        this.session = session;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public boolean save(String name, String personalNumericalCode, String address) {
        Client client = new ClientBuilder()
                .setName(name)
                .setPersonalNumericalCode(personalNumericalCode)
                .setAddress(address)
                .build();
        eventRepository.save(createEvent(ADD_CLIENT));
        return clientRepository.save(client);
    }

    @Override
    public boolean update(Long id, String name, String personalNumericalCode, String address) {
        eventRepository.save(createEvent(UPDATE_CLIENT));
        if(!name.isBlank()) {
            if(!clientRepository.update(id, "name", name)) {
                return false;
            }
        }
        if(!personalNumericalCode.isBlank()) {
            if(!clientRepository.update(id, "personal_numerical_code", personalNumericalCode)) {
                return false;
            }
        }
        if(!address.isBlank()) {
            return clientRepository.update(id, "address", address);
        }
        return true;
    }

    private Event createEvent(String action) {
        return new EventBuilder()
                .setUserId(session.getLoggedUser().get().getId())
                .setAction(action)
                .setDate(new Date())
                .build();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
}
