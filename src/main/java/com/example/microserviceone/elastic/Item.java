package com.example.microserviceone.elastic;

import lombok.*;


@Data
public class Item {
    
    String id;
    
    String name;

    public Item() {
    }

    public Item(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
