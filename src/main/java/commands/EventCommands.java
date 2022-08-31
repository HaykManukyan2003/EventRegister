package commands;

public interface EventCommands {

    int EXIT = 0;
    int ADD_EVENT = 1;
    int ADD_USER = 2;
    int SHOW_ALL_EVENTS = 3;
    int SHOW_ALL_USERS = 4;

    static void printCommands() {
        System.out.println("input '0' to exit");
        System.out.println("input '1' to add event");
        System.out.println("input '2' to add user");
        System.out.println("input '3' to display all events");
        System.out.println("input '4' to display all users");
    }
}
