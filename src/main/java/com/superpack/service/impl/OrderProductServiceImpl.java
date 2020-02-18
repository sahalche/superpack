package com.superpack.service.impl;

import com.superpack.service.OrderProductService;
import com.superpack.domain.OrderProduct;
import com.superpack.repository.OrderProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderProduct}.
 */
@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

    private final Logger log = LoggerFactory.getLogger(OrderProductServiceImpl.class);

    private final OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    /**
     * Save a orderProduct.
     *
     * @param orderProduct the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        log.debug("Request to save OrderProduct : {}", orderProduct);
        return orderProductRepository.save(orderProduct);
    }

    /**
     * Get all the orderProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderProduct> findAll(Pageable pageable) {
        log.debug("Request to get all OrderProducts");
        return orderProductRepository.findAll(pageable);
    }


    /**
     * Get one orderProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderProduct> findOne(Long id) {
        log.debug("Request to get OrderProduct : {}", id);
        return orderProductRepository.findById(id);
    }

    /**
     * Delete the orderProduct by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderProduct : {}", id);
        orderProductRepository.deleteById(id);
    }
}
