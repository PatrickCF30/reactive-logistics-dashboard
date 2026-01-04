package com.jcastillo.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trucks") // MongoDB Collection
public class Truck {

    @Id
    private String id; // MongoDB usa Strings (UUIDs) por defecto

    private String plateNumber; // Placa del camión

    // Coordenadas Geoespaciales
    private double latitude;
    private double longitude;

    private double speed; // Km/h

    private LocalDateTime timestamp;
}