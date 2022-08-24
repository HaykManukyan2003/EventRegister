package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String name;
    private String surname;
    private String email;
    private int eventId;

    public User(String name, String surname, String email, int eventId) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.eventId = eventId;
    }
}
