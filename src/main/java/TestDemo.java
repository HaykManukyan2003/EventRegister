import commands.EventCommands;
import enums.EventType;
import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class TestDemo implements EventCommands {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final UserManager userManager = new UserManager();
    private static final EventManager eventManager = new EventManager();

    public static void main(String[] args) {

        boolean isRunning = true;
        while (isRunning) {
            EventCommands.printCommands();

            int command = Integer.parseInt(SCANNER.nextLine());
            switch (command) {
                case EXIT:
                    isRunning = false;
                    break;
                case ADD_EVENT:
                    addEvent();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case SHOW_ALL_EVENTS:
                    showAllEvents();
                    break;
                case SHOW_ALL_USERS:
                    showAllUsers();
                    break;
                default:
                    System.err.println("invalid command");
            }
        }
    }

    private static void showAllUsers() {
        List<User> users = userManager.showUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void showAllEvents() {
        List<Event> events = eventManager.showEvents();
        for (Event event : events) {
            System.out.println(event);
        }
    }

    private static void addUser() {
        System.out.println("choose event id for user");
        showAllEvents();

        int eventId = Integer.parseInt(SCANNER.nextLine());
        Event event = eventManager.getById(eventId);
        if (event == null) System.err.println("invalid event id");
        else {
            System.out.println("input user's name,surname,email");
            String userDataString = SCANNER.nextLine();
            String[] userData = userDataString.split(",");

            User user = User.builder()
                    .name(userData[0])
                    .surname(userData[1])
                    .email(userData[2])
                    .event(event)
                    .build();
            userManager.addUser(user);
            System.out.println("user added");
        }
    }

    private static void addEvent() {
        System.out.println("input event's name,place,isOnline,price," +
                "eventType(MUSIC_AND_ARTS_FESTIVAL', 'FILM_FESTIVAL', 'CARNIVAL', 'SPORT_EVENT), date(yyyy-MM-dd HH:mm:ss)");
        String eventDataString = SCANNER.nextLine();
        String[] eventData = eventDataString.split(",");

        Event event;
        try {
            event = Event.builder()
                    .name(eventData[0])
                    .place(eventData[1])
                    .isOnline(Boolean.parseBoolean(eventData[2]))
                    .price(Double.parseDouble(eventData[3]))
                    .eventType(EventType.valueOf(eventData[4]))
                    .eventDate(sdf.parse(eventData[5]))
                    .build();
            eventManager.addEvent(event);
            System.out.println("event added");
        } catch (ParseException e) {
            System.err.println("invalid date format");
        }
    }
}
