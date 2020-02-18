package com.superpack.service.impl;

import com.superpack.service.OrderListService;
import com.superpack.domain.OrderList;
import com.superpack.repository.OrderListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderList}.
 */
@Service
@Transactional
public class OrderListServiceImpl implements OrderListService {

    private final Logger log = LoggerFactory.getLogger(OrderListServiceImpl.class);

    private final OrderListRepository orderListRepository;

    public OrderListServiceImpl(OrderListRepository orderListRepository) {
        this.orderListRepository = orderListRepository;
    }

    /**
     * Save a orderList.
     *
     * @param orderList the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderList save(OrderList orderList) {
        log.debug("Request to save OrderList : {}", orderList);
        return orderListRepository.save(orderList);
    }

    /**
     * Get all the orderLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderList> findAll(Pageable pageable) {
        log.debug("Request to get all OrderLists");
        return orderListRepository.findAll(pageable);
    }


    /**
     * Get one orderList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderList> findOne(Long id) {
        log.debug("Request to get OrderList : {}", id);
        return orderListRepository.findById(id);
    }

    /**
     * Delete the orderList by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderList : {}", id);
        orderListRepository.deleteById(id);
    }
}
