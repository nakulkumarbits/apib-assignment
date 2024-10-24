package com.bitspilani.fooddeliverysystem.controller;

import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.INVALID_JWT_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.AdministratorDTO;
import com.bitspilani.fooddeliverysystem.dto.AuthRequest;
import com.bitspilani.fooddeliverysystem.dto.AuthResponse;
import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.model.BlacklistedToken;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.repository.BlacklistedTokenRepository;
import com.bitspilani.fooddeliverysystem.security.JwtUtil;
import com.bitspilani.fooddeliverysystem.service.CustomUserDetailsService;
import com.bitspilani.fooddeliverysystem.service.CustomerService;
import com.bitspilani.fooddeliverysystem.service.DeliveryPersonnelService;
import com.bitspilani.fooddeliverysystem.service.RestaurantService;
import com.bitspilani.fooddeliverysystem.service.UserService;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private CustomerService customerService;

    @Mock
    private DeliveryPersonnelService deliveryPersonnelService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        CustomerDTO registeredCustomer = new CustomerDTO();

        when(userService.registerCustomer(customerDTO)).thenReturn(registeredCustomer);

        ResponseEntity<CustomerDTO> response = authController.registerCustomer(customerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registeredCustomer, response.getBody());
    }

    @Test
    void testRegisterRestaurantOwner() {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        RestaurantDTO registeredRestaurant = new RestaurantDTO();

        when(userService.registerRestaurantOwner(restaurantDTO)).thenReturn(registeredRestaurant);

        ResponseEntity<RestaurantDTO> response = authController.registerRestaurantOwner(restaurantDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registeredRestaurant, response.getBody());
    }

    @Test
    void testRegisterDeliveryPersonnel() {
        DeliveryPersonnelDTO deliveryPersonnelDTO = new DeliveryPersonnelDTO();
        DeliveryPersonnelDTO registeredPersonnel = new DeliveryPersonnelDTO();

        when(userService.registerDeliveryPersonnel(deliveryPersonnelDTO)).thenReturn(registeredPersonnel);

        ResponseEntity<DeliveryPersonnelDTO> response = authController.registerDeliveryPersonnel(deliveryPersonnelDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registeredPersonnel, response.getBody());
    }

    @Test
    void testRegisterAdministrator() {
        AdministratorDTO adminDTO = new AdministratorDTO();
        AdministratorDTO registeredAdmin = new AdministratorDTO();

        when(userService.registerAdministrator(adminDTO)).thenReturn(registeredAdmin);

        ResponseEntity<AdministratorDTO> response = authController.registerAdministrator(adminDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registeredAdmin, response.getBody());
    }

    @Test
    void testLogin_Success_ForCustomer() {
        AuthRequest authRequest = getAuthRequest();

        setupMocks(FoodDeliveryConstants.ROLE_CUSTOMER);

        Customer customer = new Customer();
        customer.setId(1L);
        when(customerService.getCustomerByUsername(FoodDeliveryTestConstants.USERNAME)).thenReturn(customer);

        when(jwtUtil.generateToken(eq(FoodDeliveryTestConstants.USERNAME), anyList(), eq(null), eq(1L), eq(null))).thenReturn("mockToken");

        ResponseEntity<?> response = authController.login(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(AuthResponse.class, response.getBody());
        assertEquals("mockToken", ((AuthResponse) response.getBody()).getToken());
    }

    @Test
    void testLogin_Success_ForRestaurant() {
        AuthRequest authRequest = getAuthRequest();

        setupMocks(FoodDeliveryConstants.ROLE_RESTAURANT_OWNER);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        when(restaurantService.getRestaurantByUsername(FoodDeliveryTestConstants.USERNAME)).thenReturn(restaurant);

        when(jwtUtil.generateToken(eq(FoodDeliveryTestConstants.USERNAME), anyList(), eq(1L), eq(null), eq(null))).thenReturn("mockToken");

        ResponseEntity<?> response = authController.login(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(AuthResponse.class, response.getBody());
        assertEquals("mockToken", ((AuthResponse) response.getBody()).getToken());
    }

    @Test
    void testLogin_Success_ForDeliveryPersonnel() {
        AuthRequest authRequest = getAuthRequest();

        setupMocks(FoodDeliveryConstants.ROLE_DELIVERY_PERSONNEL);

        DeliveryPersonnel deliveryPersonnel = new DeliveryPersonnel();
        deliveryPersonnel.setId(1L);
        when(deliveryPersonnelService.getDeliveryPersonnelByUsername(FoodDeliveryTestConstants.USERNAME)).thenReturn(deliveryPersonnel);

        when(jwtUtil.generateToken(eq(FoodDeliveryTestConstants.USERNAME), anyList(), eq(null), eq(null), eq(1L))).thenReturn("mockToken");

        ResponseEntity<?> response = authController.login(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(AuthResponse.class, response.getBody());
        assertEquals("mockToken", ((AuthResponse) response.getBody()).getToken());
    }

    @Test
    void testLogin_Failure() {
        AuthRequest authRequest = new AuthRequest();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(
            new RuntimeException("Invalid credentials"));

        ResponseEntity<?> response = authController.login(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Login failed: Invalid credentials", response.getBody());
    }

    @Test
    void testLogout() {
        String token = "Bearer mockToken";

        ResponseEntity<?> response = authController.logout(token);

        verify(blacklistedTokenRepository).save(any(BlacklistedToken.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(FoodDeliveryConstants.LOGOUT_MSG, response.getBody());
    }

    static Stream<Object[]> invalidTokenProvider() {
        return Stream.of(
            new Object[]{INVALID_JWT_TOKEN},
            new Object[]{null}
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTokenProvider")
    void testLogoutWhenTokenIsInvalid(String token) {
        ResponseEntity<?> response = authController.logout(token);
        verify(blacklistedTokenRepository, times(0)).save(any(BlacklistedToken.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void setupMocks(String role) {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        GrantedAuthority grantedAuthority = mock(GrantedAuthority.class);

        when(grantedAuthority.getAuthority()).thenReturn(role);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(
            authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        doReturn(Collections.singletonList(grantedAuthority)).when(userDetails).getAuthorities();

        when(userDetails.getUsername()).thenReturn(FoodDeliveryTestConstants.USERNAME);
    }

    private static AuthRequest getAuthRequest() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(FoodDeliveryTestConstants.USERNAME);
        authRequest.setPassword("password");
        return authRequest;
    }
}
