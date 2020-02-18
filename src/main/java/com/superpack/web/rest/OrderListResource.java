package com.superpack.web.rest;

import com.superpack.domain.OrderList;
import com.superpack.service.OrderListService;
import com.superpack.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.superpack.domain.OrderList}.
 */
@RestController
@RequestMapping("/api")
public class OrderListResource {

    private final Logger log = LoggerFactory.getLogger(OrderListResource.class);

    private static final String ENTITY_NAME = "orderList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderListService orderListService;

    public OrderListResource(OrderListService orderListService) {
        this.orderListService = orderListService;
    }

    /**
     * {@code POST  /order-lists} : Create a new orderList.
     *
     * @param orderList the orderList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderList, or with status {@code 400 (Bad Request)} if the orderList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-lists")
    public ResponseEntity<OrderList> createOrderList(@RequestBody OrderList orderList) throws URISyntaxException {
        log.debug("REST request to save OrderList : {}", orderList);
        if (orderList.getId() != null) {
            throw new BadRequestAlertException("A new orderList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderList result = orderListService.save(orderList);
        return ResponseEntity.created(new URI("/api/order-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-lists} : Updates an existing orderList.
     *
     * @param orderList the orderList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderList,
     * or with status {@code 400 (Bad Request)} if the orderList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-lists")
    public ResponseEntity<OrderList> updateOrderList(@RequestBody OrderList orderList) throws URISyntaxException {
        log.debug("REST request to update OrderList : {}", orderList);
        if (orderList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderList result = orderListService.save(orderList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-lists} : get all the orderLists.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderLists in body.
     */
    @GetMapping("/order-lists")
    public ResponseEntity<List<OrderList>> getAllOrderLists(Pageable pageable) {
        log.debug("REST request to get a page of OrderLists");
        Page<OrderList> page = orderListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-lists/:id} : get the "id" orderList.
     *
     * @param id the id of the orderList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-lists/{id}")
    public ResponseEntity<OrderList> getOrderList(@PathVariable Long id) {
        log.debug("REST request to get OrderList : {}", id);
        Optional<OrderList> orderList = orderListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderList);
    }

    /**
     * {@code DELETE  /order-lists/:id} : delete the "id" orderList.
     *
     * @param id the id of the orderList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-lists/{id}")
    public ResponseEntity<Void> deleteOrderList(@PathVariable Long id) {
        log.debug("REST request to delete OrderList : {}", id);
        orderListService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
