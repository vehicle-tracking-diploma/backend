package com.example.vts.service;

import com.example.vts.entity.Car;
import com.example.vts.entity.Report;
import com.example.vts.repository.ReportRepository;
import com.example.vts.request.ReportRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    ReportRepository reportRepository;
    CarService carService;

    public Report addReport(ReportRequest reportRequest) {
        Report report1 = new Report();
        report1.setTitle(reportRequest.getTitle());
        report1.setDetail(reportRequest.getDetail());
        report1.setLocalDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        Car car = carService.getCarByGovID(reportRequest.getGovId());
        if (car == null) return null;
        List<Report> reports;
        if (car.getReports() == null) {
            reports = new ArrayList<>();
        } else {
            reports = car.getReports();
        }
        reportRepository.save(report1);
        reports.add(report1);   
        carService.addReport(car, reports);
        return report1;
    }

}
