package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.AdministratorDTO;
import com.bitspilani.fooddeliverysystem.model.Administrator;

public class AdminConvertor {

    public static Administrator toAdmin(AdministratorDTO administratorDTO) {
        Administrator administrator = new Administrator();
        administrator.setUsername(administratorDTO.getUsername());
        administrator.setPassword(administratorDTO.getPassword());
        return administrator;
    }

    public static AdministratorDTO toAdminDTO(Administrator administrator) {
        AdministratorDTO administratorDTO = new AdministratorDTO();
        administratorDTO.setUsername(administrator.getUsername());
        return administratorDTO;
    }
}
