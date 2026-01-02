package matalvesdev.order_ms.factory;

import matalvesdev.order_ms.entity.OrderItems;
import matalvesdev.order_ms.entity.OrderMsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


import java.math.BigDecimal;
import java.util.List;

public class OrderEntityFactory {

    public static OrderMsEntity build() {
        var items = new OrderItems("notebook", 1, BigDecimal.valueOf(20.50));

        var entity = new OrderMsEntity();
        entity.setOrderId(1L);
        entity.setCustomerId(2L);
        entity.setTotal(BigDecimal.valueOf(20.50));
        entity.setItems(List.of(items));

        return entity;
    }

    public static Page<OrderMsEntity> buildWithPage() {
        return new PageImpl<>(List.of(build()));
    }
}