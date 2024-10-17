package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.AddressConvertor;
import com.bitspilani.fooddeliverysystem.utils.CustomerConvertor;
import com.bitspilani.fooddeliverysystem.utils.DeliveryPersonnelConvertor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonnelService {

    @Autowired
    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DeliveryPersonnelDTO getDeliveryPersonnel(String username) {
        User user = userRepository.findByUsernameAndRole(username, UserRole.DELIVERY_PERSONNEL);
        if (user != null) {
            DeliveryPersonnel deliveryPersonnel = deliveryPersonnelRepository.findByUser(user);
            return DeliveryPersonnelConvertor.toDeliveryPersonnelDTO(deliveryPersonnel);
        }
        throw new UserNotFoundException("Customer does not exist.");
    }

    public List<DeliveryPersonnelDTO> getDeliveryPersonnels() {
        List<DeliveryPersonnel> personnels = deliveryPersonnelRepository.findAll();
        return DeliveryPersonnelConvertor.toDeliveryPersonnelList(personnels);
    }

    public DeliveryPersonnelDTO updateDeliveryPersonnel(DeliveryPersonnelDTO deliveryPersonnelDTO, String username) {
        if (!username.equals(deliveryPersonnelDTO.getUsername())) {
            throw new UsernameMismatchException("Username mismatch in request and body.");
        }
        User user = userRepository.findByUsernameAndRole(username, UserRole.DELIVERY_PERSONNEL);
        if (user != null) {
            DeliveryPersonnel deliveryPersonnel = deliveryPersonnelRepository.findByUser(user);
            copyFields(deliveryPersonnel, deliveryPersonnelDTO);
            DeliveryPersonnel savedDeliveryPersonnel = deliveryPersonnelRepository.save(deliveryPersonnel);
            return DeliveryPersonnelConvertor.toDeliveryPersonnelDTO(savedDeliveryPersonnel);
        }
        throw new UserNotFoundException("Delivery personnel does not exist.");
    }

    private void copyFields(DeliveryPersonnel deliveryPersonnel, DeliveryPersonnelDTO deliveryPersonnelDTO) {
        deliveryPersonnel.setVehicleAddress(AddressConvertor.toAddress(deliveryPersonnelDTO.getAddress()));
        deliveryPersonnel.setVehicleType(deliveryPersonnelDTO.getVehicleType());
        deliveryPersonnel.getUser().setPassword(passwordEncoder.encode(deliveryPersonnelDTO.getPassword()));
    }

    // Methods to manage delivery personnel
}