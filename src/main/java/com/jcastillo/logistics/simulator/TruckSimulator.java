package com.jcastillo.logistics.simulator;

import com.jcastillo.logistics.entity.Truck;
import com.jcastillo.logistics.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class TruckSimulator implements CommandLineRunner {

    private final TruckRepository truckRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        log.info("🚦 Iniciando simulación de tráfico reactivo...");

        // 1. Borrar datos viejos al iniciar (opcional, para limpiar)
        truckRepository.deleteAll().subscribe();

        // 2. Generar un flujo infinito de eventos cada 500ms
        Flux.interval(Duration.ofMillis(500))
                .flatMap(tick -> {
                    // Simulamos 5 camiones moviéndose a la vez en cada "tick"
                    return Flux.range(1, 5)
                            .map(this::crearCamionSimulado);
                })
                .flatMap(truckRepository::save) // Guardar en Mongo (Async)
                .subscribe(
                        truck -> log.info("🚚 Camión {} moviéndose -> Lat: {}, Lon: {}",
                                truck.getPlateNumber(), truck.getLatitude(), truck.getLongitude())
                );
    }

    private Truck crearCamionSimulado(int id) {
        double latBase = -12.046374; // Lima, Perú
        double lonBase = -77.042793;

        // Variación aleatoria pequeña para simular movimiento
        double lat = latBase + (random.nextDouble() * 0.1);
        double lon = lonBase + (random.nextDouble() * 0.1);

        return new Truck(
                UUID.randomUUID().toString(),
                "TRUCK-" + String.format("%03d", id),
                lat,
                lon,
                random.nextDouble() * 100, // Velocidad 0-100 km/h
                LocalDateTime.now()
        );
    }
}