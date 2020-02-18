package com.superpack.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.superpack.web.rest.TestUtil;

public class OrderListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderList.class);
        OrderList orderList1 = new OrderList();
        orderList1.setId(1L);
        OrderList orderList2 = new OrderList();
        orderList2.setId(orderList1.getId());
        assertThat(orderList1).isEqualTo(orderList2);
        orderList2.setId(2L);
        assertThat(orderList1).isNotEqualTo(orderList2);
        orderList1.setId(null);
        assertThat(orderList1).isNotEqualTo(orderList2);
    }
}
