package matalvesdev.order_ms.repository;

import matalvesdev.order_ms.entity.OrderMsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OrderMsRepository extends MongoRepository<OrderMsEntity, Long> {

    Page<OrderMsEntity> findAllByCustomerId(Long customerId, PageRequest pageRequest);
}