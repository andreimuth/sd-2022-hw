package repository.client;

import controller.response.Response;
import model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    List<Client> findAll();

    Optional<Client> findById(Long id);

    boolean save(Client client);

    void removeAll();

    Response<Boolean> existsByPnc(String personalNumericalCode);

    boolean update(Long id, String column, String newValue);

}
