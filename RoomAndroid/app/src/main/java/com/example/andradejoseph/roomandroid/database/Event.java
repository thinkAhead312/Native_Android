package com.example.andradejoseph.roomandroid.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.example.andradejoseph.roomandroid.database.Event.TABLE_NAME;

/**
 * Created by ANDRADEJOSEPH on 6/28/2017.
 */

@Entity(tableName = TABLE_NAME)
public class Event {
    public static final String TABLE_NAME = "events";
    public static final String DATE_FIELD = "date";

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    @ColumnInfo(name = DATE_FIELD)
    private LocalDateTime date;

    public Event(int id, String name, String description, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    public long getDaysUntil() {
        return ChronoUnit.DAYS.between(LocalDateTime.now(), getDate());
    }


}