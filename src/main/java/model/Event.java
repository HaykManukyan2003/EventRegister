package model;

import enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private int id;
    private String name;
    private String place;
    private boolean isOnline;
    private double price;
    private EventType eventType;

    public Event(String name, String place, boolean isOnline, double price, EventType eventType) {
        this.name = name;
        this.place = place;
        this.isOnline = isOnline;
        this.price = price;
        this.eventType = eventType;
    }
}
