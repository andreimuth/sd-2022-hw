package model.validator;

import controller.response.Response;
import model.Client;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientValidator {

    private final ClientRepository clientRepository;
    private final List<String> errors;
    private final int PNC_LENGTH = 13;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        errors = new ArrayList<>();
    }

    public void validate(String name, String personalNumericalCode, String address) {
        errors.clear();
        validateName(name);
        validatePncLength(personalNumericalCode);
        validatePnc(personalNumericalCode);
        validatePncUniqueness(personalNumericalCode);
        validateAddress(address);
    }

    public Optional<Long> validateIdToUpdate(String idString) {
        errors.clear();
        if(!idString.matches("[0-9]+")) {
            errors.add("Id must be an integer");
            return Optional.empty();
        }
        Long id = Long.parseLong(idString);
        Optional<Client> optionalClient = clientRepository.findById(id);
        if(optionalClient.isEmpty()) {
            errors.add("Client with id = " + id + " does not exist");
            return Optional.empty();
        }
        return Optional.of(id);
    }

    public void validateUpdate(String name, String personalNumericalCode, String address) {
        errors.clear();
        validateOneUpdateExistence(name, personalNumericalCode, address);
        if(!personalNumericalCode.isEmpty()) {
            validatePncLength(personalNumericalCode);
            validatePnc(personalNumericalCode);
            validatePncUniqueness(personalNumericalCode);
        }
    }

    private void validateName(String name) {
        if(name.isEmpty()) {
            errors.add("Name cannot be empty");
        }
    }

    private void validatePncLength(String personalNumericalCode) {
        if(personalNumericalCode.length() != PNC_LENGTH) {
            errors.add("Personal numerical code must consist of 13 digits");
        }
    }

    private void validatePnc(String personalNumericalCode) {
        if(!personalNumericalCode.matches("[0-9]+")) {
            errors.add("Personal numerical code must contain only digits");
        }
    }

    private void validatePncUniqueness(String personalNumericalCode) {
        final Response<Boolean> response = clientRepository.existsByPnc(personalNumericalCode);
        if(response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if(data) {
                errors.add("Personal numerical code must be unique");
            }
        }
    }

    private void validateAddress(String address) {
        if(address.isEmpty()) {
            errors.add("Address cannot be empty");
        }
    }

    private void validateOneUpdateExistence(String name, String personalNumericalCode, String address) {
        if(name.isEmpty() && personalNumericalCode.isEmpty() && address.isEmpty()) {
            errors.add("Update at least one field");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
