package service.client;

import model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();

    boolean save(String name, String personalNumericalCode, String address);

    boolean update(Long id, String name, String personalNumericalCode, String address);

    Optional<Client> findById(Long id);

}
