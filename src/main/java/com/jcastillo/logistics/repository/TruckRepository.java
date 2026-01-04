package com.jcastillo.logistics.repository;

import com.jcastillo.logistics.entity.Truck;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TruckRepository extends ReactiveMongoRepository<Truck, String> {

    @Tailable
    Flux<Truck> findWithTailableCursorBy();
}
