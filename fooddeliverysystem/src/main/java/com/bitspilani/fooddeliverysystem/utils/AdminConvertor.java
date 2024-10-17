package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.AdministratorDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.model.Administrator;
import com.bitspilani.fooddeliverysystem.model.User;

public class AdminConvertor {

    public static Administrator toAdmin(AdministratorDTO administratorDTO) {
        Administrator administrator = new Administrator();
        User user = new User();
        user.setUsername(administratorDTO.getUsername());
        user.setPassword(administratorDTO.getPassword());
        user.setRole(UserRole.ADMIN);
        administrator.setUser(user);

        return administrator;
    }

    public static AdministratorDTO toAdminDTO(Administrator administrator) {
        AdministratorDTO administratorDTO = new AdministratorDTO();
        administratorDTO.setUsername(administrator.getUser().getUsername());
        return administratorDTO;
    }
}
