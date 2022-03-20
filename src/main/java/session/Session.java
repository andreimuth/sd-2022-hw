package session;

import model.User;

import java.util.Optional;

public class Session {

    private Optional<User> loggedUser;

    public Session() {
        this.loggedUser = Optional.empty();
    }

    public Optional<User> getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = Optional.of(loggedUser);
    }

    public void logout() {
        this.loggedUser = Optional.empty();
    }
}
