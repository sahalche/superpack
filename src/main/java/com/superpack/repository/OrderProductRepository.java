package com.superpack.repository;

import com.superpack.domain.OrderProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
