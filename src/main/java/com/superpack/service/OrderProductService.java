package com.superpack.service;

import com.superpack.domain.OrderProduct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link OrderProduct}.
 */
public interface OrderProductService {

    /**
     * Save a orderProduct.
     *
     * @param orderProduct the entity to save.
     * @return the persisted entity.
     */
    OrderProduct save(OrderProduct orderProduct);

    /**
     * Get all the orderProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderProduct> findAll(Pageable pageable);


    /**
     * Get the "id" orderProduct.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderProduct> findOne(Long id);

    /**
     * Delete the "id" orderProduct.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
