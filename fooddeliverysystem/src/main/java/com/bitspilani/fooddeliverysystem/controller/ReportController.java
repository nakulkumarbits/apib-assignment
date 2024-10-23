package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.dto.ReportDTO;
import com.bitspilani.fooddeliverysystem.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/reports")
public class ReportController {

  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping("/popular-restaurants")
  public ResponseEntity<ReportDTO> generatePopularRestaurantsReport() {
    return ResponseEntity.ok(reportService.generatePopularRestaurantsReport());
  }

  @GetMapping("/average-delivery-time")
  public ResponseEntity<ReportDTO> generateAverageDeliveryTimeReport() {
    return ResponseEntity.ok(reportService.generateAverageDeliveryTimeReport());
  }

  @GetMapping("/order-trends")
  public ResponseEntity<ReportDTO> generateOrderTrendsReport(@RequestParam String period) {
    return ResponseEntity.ok(reportService.generateOrderTrendsReport(period));
  }
}
