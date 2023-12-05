package org.example.registration.registrations;

public class RegistrationDto {
    private String login;
    private String password;
    private String status;

    public RegistrationDto(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    // Геттеры
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    // Сеттеры
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
