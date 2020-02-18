package com.superpack.web.rest;

import com.superpack.SuperpackApp;
import com.superpack.domain.OrderList;
import com.superpack.repository.OrderListRepository;
import com.superpack.service.OrderListService;
import com.superpack.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.superpack.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderListResource} REST controller.
 */
@SpringBootTest(classes = SuperpackApp.class)
public class OrderListResourceIT {

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USER_IDP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_USER_IDP_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PAYMENT_DONE = false;
    private static final Boolean UPDATED_PAYMENT_DONE = true;

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrderListMockMvc;

    private OrderList orderList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderListResource orderListResource = new OrderListResource(orderListService);
        this.restOrderListMockMvc = MockMvcBuilders.standaloneSetup(orderListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderList createEntity(EntityManager em) {
        OrderList orderList = new OrderList()
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .createdDate(DEFAULT_CREATED_DATE)
            .userIdpCode(DEFAULT_USER_IDP_CODE)
            .paymentDone(DEFAULT_PAYMENT_DONE);
        return orderList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderList createUpdatedEntity(EntityManager em) {
        OrderList orderList = new OrderList()
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .userIdpCode(UPDATED_USER_IDP_CODE)
            .paymentDone(UPDATED_PAYMENT_DONE);
        return orderList;
    }

    @BeforeEach
    public void initTest() {
        orderList = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderList() throws Exception {
        int databaseSizeBeforeCreate = orderListRepository.findAll().size();

        // Create the OrderList
        restOrderListMockMvc.perform(post("/api/order-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderList)))
            .andExpect(status().isCreated());

        // Validate the OrderList in the database
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeCreate + 1);
        OrderList testOrderList = orderListList.get(orderListList.size() - 1);
        assertThat(testOrderList.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testOrderList.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrderList.getUserIdpCode()).isEqualTo(DEFAULT_USER_IDP_CODE);
        assertThat(testOrderList.isPaymentDone()).isEqualTo(DEFAULT_PAYMENT_DONE);
    }

    @Test
    @Transactional
    public void createOrderListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderListRepository.findAll().size();

        // Create the OrderList with an existing ID
        orderList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderListMockMvc.perform(post("/api/order-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderList)))
            .andExpect(status().isBadRequest());

        // Validate the OrderList in the database
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderLists() throws Exception {
        // Initialize the database
        orderListRepository.saveAndFlush(orderList);

        // Get all the orderListList
        restOrderListMockMvc.perform(get("/api/order-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderList.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].userIdpCode").value(hasItem(DEFAULT_USER_IDP_CODE)))
            .andExpect(jsonPath("$.[*].paymentDone").value(hasItem(DEFAULT_PAYMENT_DONE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOrderList() throws Exception {
        // Initialize the database
        orderListRepository.saveAndFlush(orderList);

        // Get the orderList
        restOrderListMockMvc.perform(get("/api/order-lists/{id}", orderList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderList.getId().intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.userIdpCode").value(DEFAULT_USER_IDP_CODE))
            .andExpect(jsonPath("$.paymentDone").value(DEFAULT_PAYMENT_DONE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderList() throws Exception {
        // Get the orderList
        restOrderListMockMvc.perform(get("/api/order-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderList() throws Exception {
        // Initialize the database
        orderListService.save(orderList);

        int databaseSizeBeforeUpdate = orderListRepository.findAll().size();

        // Update the orderList
        OrderList updatedOrderList = orderListRepository.findById(orderList.getId()).get();
        // Disconnect from session so that the updates on updatedOrderList are not directly saved in db
        em.detach(updatedOrderList);
        updatedOrderList
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .userIdpCode(UPDATED_USER_IDP_CODE)
            .paymentDone(UPDATED_PAYMENT_DONE);

        restOrderListMockMvc.perform(put("/api/order-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderList)))
            .andExpect(status().isOk());

        // Validate the OrderList in the database
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeUpdate);
        OrderList testOrderList = orderListList.get(orderListList.size() - 1);
        assertThat(testOrderList.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testOrderList.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrderList.getUserIdpCode()).isEqualTo(UPDATED_USER_IDP_CODE);
        assertThat(testOrderList.isPaymentDone()).isEqualTo(UPDATED_PAYMENT_DONE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderList() throws Exception {
        int databaseSizeBeforeUpdate = orderListRepository.findAll().size();

        // Create the OrderList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderListMockMvc.perform(put("/api/order-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderList)))
            .andExpect(status().isBadRequest());

        // Validate the OrderList in the database
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderList() throws Exception {
        // Initialize the database
        orderListService.save(orderList);

        int databaseSizeBeforeDelete = orderListRepository.findAll().size();

        // Delete the orderList
        restOrderListMockMvc.perform(delete("/api/order-lists/{id}", orderList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
