package com.bitspilani.fooddeliverysystem.service;

import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.ADMIN_TOKEN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.CUSTOMER_TOKEN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.DELIVERY_TOKEN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.RESTAURANT_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.DeliveryResponseDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderItemRequestDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderRequestDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderResponseDTO;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.exceptions.InvalidRequestException;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import com.bitspilani.fooddeliverysystem.model.OrderItem;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.MenuItemRepository;
import com.bitspilani.fooddeliverysystem.repository.OrderDetailRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantRepository;
import com.bitspilani.fooddeliverysystem.security.JwtUtil;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceTest {

    @Mock
    OrderDetailRepository orderDetailRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    MenuItemRepository menuItemRepository;
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateOrderFailForInvalidRequestException() {
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> orderService.createOrder(new OrderRequestDTO(), CUSTOMER_TOKEN));
        assertEquals(FoodDeliveryConstants.CUSTOMER_ID_INCORRECT, exception.getMessage());
    }

    @Test
    void testCreateWhenCustomerNotFound() {
        when(jwtUtil.extractCustomerIdFromToken(anyString())).thenReturn(Long.valueOf(1));
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> orderService.createOrder(new OrderRequestDTO(), CUSTOMER_TOKEN));
        assertEquals(FoodDeliveryConstants.CUSTOMER_ID_INCORRECT, exception.getMessage());
    }

    @Test
    void testCreateOrderWhenRestaurantNotFound() {
        when(jwtUtil.extractCustomerIdFromToken(anyString())).thenReturn(Long.valueOf(1));
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer()));
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setCustomerId(1L);
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> orderService.createOrder(orderRequestDTO, CUSTOMER_TOKEN));
        assertEquals(FoodDeliveryConstants.RESTAURANT_ID_INCORRECT, exception.getMessage());
    }

    @Test
    void testCreateOrder() {
        when(jwtUtil.extractCustomerIdFromToken(anyString())).thenReturn(Long.valueOf(1));
        when(customerRepository.findById(any())).thenReturn(Optional.of(getCustomer()));
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(new Restaurant()));
        when(menuItemRepository.findById(any())).thenReturn(Optional.of(getMenuItem()));

        OrderDetail orderDetail = getOrderDetail();
        when(orderDetailRepository.save(any())).thenReturn(orderDetail);

        OrderRequestDTO orderRequestDTO = getOrderRequestDTO();
        OrderResponseDTO result = orderService.createOrder(orderRequestDTO, CUSTOMER_TOKEN);
        assertNotNull(result);
    }

    @Test
    void testUpdateOrderStatusWhenOrderNotFoundException() {
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> orderService.updateOrderStatus(1L, OrderStatus.AWAITING_CONFIRMATION,
                CUSTOMER_TOKEN));
        assertEquals(FoodDeliveryConstants.ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testUpdateOrderStatusToSameState() {
        OrderDetail orderDetail = getOrderDetail();
        orderDetail.setOrderStatus(OrderStatus.DELIVERED);
        when(orderDetailRepository.findById(any())).thenReturn(Optional.of(orderDetail));
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> orderService.updateOrderStatus(1L, OrderStatus.DELIVERED,
                CUSTOMER_TOKEN));
        assertEquals(FoodDeliveryConstants.ORDER_SAME_STATUS_ERROR, exception.getMessage());
    }

    @Test
    void testUpdateOrderStatusWhenRestaurantTriedToUpdateInvalidStatus() {
        OrderDetail orderDetail = getOrderDetail();
        orderDetail.setOrderStatus(OrderStatus.READY_FOR_DELIVERY);
        when(jwtUtil.extractOwnerIdFromToken(anyString())).thenReturn(Long.valueOf(5));
        when(orderDetailRepository.findById(any())).thenReturn(Optional.of(orderDetail));
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> orderService.updateOrderStatus(1L, OrderStatus.OUT_FOR_DELIVERY,
                RESTAURANT_TOKEN));
        assertTrue(exception.getMessage().contains(FoodDeliveryConstants.RESTAURANT_STATUS_ERROR));
    }

    @Test
    void testUpdateOrderStatusWhenDeliveryPersonnelTriedToUpdateInvalidStatus() {
        OrderDetail orderDetail = getOrderDetail();
        orderDetail.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
        when(orderDetailRepository.findById(any())).thenReturn(Optional.of(orderDetail));
        when(jwtUtil.extractDeliveryPersonnelIdFromToken(anyString())).thenReturn(Long.valueOf(5));
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> orderService.updateOrderStatus(1L, OrderStatus.ACCEPTED,
                DELIVERY_TOKEN));
        assertTrue(exception.getMessage().contains(FoodDeliveryConstants.DELIVERY_STATUS_ERROR));
    }

    @Test
    void testUpdateOrderStatus() {
        OrderDetail orderDetail = getOrderDetail();
        orderDetail.setOrderStatus(OrderStatus.AWAITING_CONFIRMATION);
        when(orderDetailRepository.findById(any())).thenReturn(Optional.of(orderDetail));
        when(jwtUtil.extractOwnerIdFromToken(anyString())).thenReturn(Long.valueOf(5));
        when(orderDetailRepository.save(any())).thenReturn(orderDetail);

        OrderResponseDTO result = orderService.updateOrderStatus(1L, OrderStatus.ACCEPTED,
            RESTAURANT_TOKEN);
        assertNotNull(result);
        verify(orderDetailRepository).save(any());
    }

    @Test
    void testGetOrderWhenOrderNotFound() {
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> orderService.getOrder(1L));
        assertEquals(FoodDeliveryConstants.ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetOrder() {
        when(orderDetailRepository.findById(any())).thenReturn(Optional.of(getOrderDetail()));
        OrderResponseDTO responseDTO = orderService.getOrder(1L);
        assertNotNull(responseDTO);
    }

    @Test
    void testGetOrders() {
        when(orderDetailRepository.findByCustomerIdOrderByCreatedDateDesc(any())).thenReturn(List.of(getOrderDetail()));
        when(jwtUtil.extractCustomerIdFromToken(anyString())).thenReturn(Long.valueOf(1));

        List<OrderResponseDTO> result = orderService.getOrders(CUSTOMER_TOKEN);
        assertNotNull(result);
    }

    @Test
    void testGetIncomingOrders() {
        when(orderDetailRepository.findByRestaurantIdAndOrderStatusIn(any(), any())).thenReturn(
            List.of(getOrderDetail()));
        when(jwtUtil.extractOwnerIdFromToken(anyString())).thenReturn(Long.valueOf(1));

        List<OrderResponseDTO> result = orderService.getIncomingOrders(RESTAURANT_TOKEN);
        assertNotNull(result);
    }

    @Test
    void testGetIncomingOrdersForAdmin() {
        when(orderDetailRepository.findByOrderStatusIn(any())).thenReturn(List.of(getOrderDetail()));
        when(jwtUtil.hasAdminRole(anyString())).thenReturn(true);
        List<OrderResponseDTO> result = orderService.getIncomingOrders(ADMIN_TOKEN);
        assertNotNull(result);
    }


    @Test

    void testGetDeliverables() {
        when(orderDetailRepository.findByOrderStatusIn(any())).thenReturn(List.of(getOrderDetail()));

        List<DeliveryResponseDTO> result = orderService.getDeliverables(DELIVERY_TOKEN);
        assertNotNull(result);
    }

    private static OrderRequestDTO getOrderRequestDTO() {
        OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setCustomerId(1L);
        orderRequestDTO.setOrderItems(List.of(orderItemRequestDTO));
        return orderRequestDTO;
    }

    private OrderDetail getOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setCustomer(getCustomer());
        orderDetail.setRestaurant(new Restaurant());
        orderDetail.setOrderItems(getOrderItems());
        orderDetail.setTotalAmount(100.0);
        return orderDetail;
    }

    private List<OrderItem> getOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(1);
        orderItem.setPrice(100.0);
        orderItem.setTotalPrice(100.0);
        orderItem.setMenuItem(getMenuItem());
        orderItems.add(orderItem);
        return orderItems;
    }

    private MenuItem getMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(10.0);
        return menuItem;
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Smith");
        return customer;
    }
}