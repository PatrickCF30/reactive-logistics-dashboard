# Dashboard de Logística en Tiempo Real (Spring WebFlux + MongoDB)

Este proyecto es una demostración de **Programación Reactiva (Non-blocking I/O)** utilizando Spring WebFlux. Simula una flota de camiones enviando telemetría GPS masiva y la visualiza en un mapa en tiempo real sin necesidad de recargar la página, utilizando un solo hilo para manejar múltiples 
conexiones de streaming.

## Características Clave

* **Reactive Stack Completo:** Desde el controlador hasta la base de datos (Netty + Spring WebFlux + Reactive MongoDB).
* **Server-Sent Events (SSE):** Streaming de datos unidireccional eficiente hacia el frontend.
* **Tailable Cursors:** Uso de cursores infinitos de MongoDB para "escuchar" cambios en la colección en tiempo real.
* **Geoespacial:** Manejo de coordenadas (Latitud/Longitud) y visualización en mapa interactivo.
* **Full Stack:** Backend Java 21 y Frontend ligero con HTML5 + Leaflet.js.

## 🛠️ Tech Stack

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.5 (WebFlux)
* **Base de Datos:** MongoDB (Dockerizada)
* **Frontend:** Leaflet.js (Mapas), EventSource API (Nativa del navegador)
* **Herramientas:** Docker Compose, Lombok.

## Arquitectura

1.  **TruckSimulator:** Un componente Java genera eventos de ubicación aleatorios para 5 camiones cada 500ms y los guarda en MongoDB.
2.  **MongoDB (Capped Collection):** La colección `trucks` está configurada como circular (tamaño fijo) para soportar cursores `tailable`.
3.  **Reactive Repository:** Spring Data expone un `Flux<Truck>` que nunca se cierra, emitiendo cada nuevo documento insertado.
4.  **Controller SSE:** El endpoint `/api/trucks/stream` transmite estos eventos al navegador como `text/event-stream`.
5.  **Frontend:** Un mapa Leaflet consume el stream y mueve los marcadores en vivo.

## Cómo ejecutarlo

### 1. Levantar la Base de Datos
```bash
docker-compose up -d
