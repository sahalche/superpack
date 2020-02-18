package com.superpack.service;

import com.superpack.domain.OrderList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link OrderList}.
 */
public interface OrderListService {

    /**
     * Save a orderList.
     *
     * @param orderList the entity to save.
     * @return the persisted entity.
     */
    OrderList save(OrderList orderList);

    /**
     * Get all the orderLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderList> findAll(Pageable pageable);


    /**
     * Get the "id" orderList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderList> findOne(Long id);

    /**
     * Delete the "id" orderList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
