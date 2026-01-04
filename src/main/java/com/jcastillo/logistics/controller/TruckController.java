package com.jcastillo.logistics.controller;

import com.jcastillo.logistics.entity.Truck;
import com.jcastillo.logistics.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/trucks")
@RequiredArgsConstructor
public class TruckController {

    private final TruckRepository truckRepository;

    // Retorna todos los datos y CIERRA la conexión.
    @GetMapping
    public Flux<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    // Endpoint SSE (Server-Sent Events)
    // Mantiene la conexión ABIERTA indefinidamente.
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Truck> streamTrucks() {
        return truckRepository.findWithTailableCursorBy();
    }
}