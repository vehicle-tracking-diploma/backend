package com.example.vts.service;

import com.example.vts.entity.Report;
import com.example.vts.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    ReportRepository reportRepository;

    public Report addCar(Report report) {
        if (reportRepository.existsById(report.getId())) {
            return null;
        }
        return reportRepository.save(report);
    }

}
