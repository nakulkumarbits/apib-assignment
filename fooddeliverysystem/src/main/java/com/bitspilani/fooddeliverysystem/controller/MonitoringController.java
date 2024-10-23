package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.dto.PlatformActivityDTO;
import com.bitspilani.fooddeliverysystem.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/monitoring")
public class MonitoringController {

  private final MonitoringService monitoringService;

  public MonitoringController(MonitoringService monitoringService) {
    this.monitoringService = monitoringService;
  }

  @GetMapping("/platform-activity")
  public ResponseEntity<PlatformActivityDTO> getPlatformActivity() {
    return ResponseEntity.ok(monitoringService.getPlatformActivity());
  }
}
