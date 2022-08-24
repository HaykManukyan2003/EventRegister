import enums.EventType;
import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class TestDemo {

    public static void main(String[] args) {

        UserManager userManager = new UserManager();
        EventManager eventManager = new EventManager();

        try {
            eventManager.addEvent(new Event("DerbyDeItalia", "Italy,Turin(Allianz Arena)",
                    false, 75.00, EventType.SPORT_EVENT));
            eventManager.addEvent(new Event("Picasso Memorial", "Argentina,Buenos Ares",
                    false, 42.00, EventType.MUSIC_AND_ARTS_FESTIVAL));
            eventManager.addEvent(new Event("World Chess Championship", "Iceland,Reykjavik)",
                    false, 350.00, EventType.SPORT_EVENT));

            userManager.addUser(new User("Poghos", "Poghosyan", "po@gmail.com", 3));
            userManager.addUser(new User("Petros", "Petrosyan", "pe@gmail.com", 1));
            userManager.addUser(new User("Martiros", "Martirosyan", "martiros@gmail.com", 2));

            List<Event> events = eventManager.showEvents();
            for (Event event : events) {
                System.out.println(event);
            }

            List<User> users = userManager.showUsers();
            for (User user : users) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
