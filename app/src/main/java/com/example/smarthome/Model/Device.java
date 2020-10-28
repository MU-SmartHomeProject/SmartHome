package com.example.smarthome.Model;

import java.io.Serializable;

public class Device implements Serializable {
    int id;
    String name;
    String type;
    int status;         // 0 means off 1 means on
    int intensity;      // 0 to 100, low to bright
    int color;          // 0 to 100, cool to warm

    public Device(int id,
                  String name,
                  String type,
                  int status,
                  int intensity,
                  int color) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.intensity = intensity;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public int getIntensity() {
        return intensity;
    }

    public int getColor() {
        return color;
    }
}
