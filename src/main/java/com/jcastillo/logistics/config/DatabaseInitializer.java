package com.jcastillo.logistics.config;

import com.jcastillo.logistics.entity.Truck;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer {

    private final ReactiveMongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        mongoTemplate.collectionExists(Truck.class)
                .flatMap(exists -> {
                    if (!exists) {
                        log.info("🛠️ Creando colección Capped 'trucks'...");
                        return mongoTemplate.createCollection(
                                Truck.class,
                                CollectionOptions.empty()
                                        .capped()
                                        .size(100000)
                                        .maxDocuments(1000)
                        );
                    }
                    return Mono.empty();
                })
                .subscribe();
    }
}