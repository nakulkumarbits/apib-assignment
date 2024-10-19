package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.AdministratorDTO;
import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantDTO;
import com.bitspilani.fooddeliverysystem.model.Administrator;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.RestaurantDeliveryZone;
import com.bitspilani.fooddeliverysystem.model.RestaurantOpeningDetail;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantDeliveryZoneRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOpeningDetailRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantRepository;
import com.bitspilani.fooddeliverysystem.utils.AdminConvertor;
import com.bitspilani.fooddeliverysystem.utils.CustomerConvertor;
import com.bitspilani.fooddeliverysystem.utils.DeliveryPersonnelConvertor;
import com.bitspilani.fooddeliverysystem.utils.DeliveryZoneConvertor;
import com.bitspilani.fooddeliverysystem.utils.OpeningHourConvertor;
import com.bitspilani.fooddeliverysystem.utils.RestaurantConvertor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private RestaurantOpeningDetailRepository restaurantOpeningDetailRepository;

    @Autowired
    private RestaurantDeliveryZoneRepository restaurantDeliveryZoneRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerConvertor.toCustomer(customerDTO);
        customer.getUser().setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerConvertor.toCustomerDTO(savedCustomer);
    }

    @Transactional
    public RestaurantDTO registerRestaurantOwner(RestaurantDTO dto) {
        Restaurant restaurant = RestaurantConvertor.toEntity(dto);
        restaurant.getUser().setPassword(passwordEncoder.encode(restaurant.getUser().getPassword()));
        Restaurant savedOwner = restaurantRepository.save(restaurant);

        List<RestaurantOpeningDetail> openingDetails = OpeningHourConvertor.toEntityList(dto.getOpeningHours());
        openingDetails.forEach(openingDetail -> openingDetail.setRestaurant(savedOwner));

        List<RestaurantOpeningDetail> openingDetailList = restaurantOpeningDetailRepository.saveAll(openingDetails);// Batch save

        List<RestaurantDeliveryZone> deliveryZones = DeliveryZoneConvertor.toEntityList(dto.getDeliveryZones());
        deliveryZones.forEach(deliveryZone -> deliveryZone.setRestaurant(savedOwner));

        List<RestaurantDeliveryZone> deliveryZoneList = restaurantDeliveryZoneRepository.saveAll(deliveryZones);

        RestaurantDTO restaurantDTO = RestaurantConvertor.toDTO(savedOwner);
        restaurantDTO.setOpeningHours(OpeningHourConvertor.toDTOList(openingDetailList));
        restaurantDTO.setDeliveryZones(DeliveryZoneConvertor.toDTOList(deliveryZoneList));
        return restaurantDTO;
    }

    public DeliveryPersonnelDTO registerDeliveryPersonnel(DeliveryPersonnelDTO deliveryPersonnelDTO) {
        DeliveryPersonnel personnel = DeliveryPersonnelConvertor.toDeliveryPersonnel(deliveryPersonnelDTO);
        personnel.getUser().setPassword(passwordEncoder.encode(personnel.getUser().getPassword()));
        DeliveryPersonnel deliveryPersonnel = deliveryPersonnelRepository.save(personnel);
        return DeliveryPersonnelConvertor.toDeliveryPersonnelDTO(deliveryPersonnel);
    }

    public AdministratorDTO registerAdministrator(AdministratorDTO admin) {
        Administrator administrator = AdminConvertor.toAdmin(admin);
        administrator.getUser().setPassword(passwordEncoder.encode(administrator.getUser().getPassword()));
        Administrator savedAdmin = administratorRepository.save(administrator);
        return AdminConvertor.toAdminDTO(savedAdmin);
    }
}
