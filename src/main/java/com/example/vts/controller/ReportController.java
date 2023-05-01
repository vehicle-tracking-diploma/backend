package com.example.vts.controller;

import com.example.vts.entity.Report;
import com.example.vts.request.ReportRequest;
import com.example.vts.service.CarService;
import com.example.vts.service.ReportService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class ReportController {
    ReportService reportService;
    CarService carService;

    @PostMapping("/report")
    public ResponseEntity<?> addReport(@RequestBody ReportRequest reportRequest) {
        Report newReport = reportService.addReport(reportRequest);
        if (newReport == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not add the report");
        }
        return ResponseEntity.status(HttpStatus.OK).body(newReport);
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getAllReportsByGovId(@RequestParam String govId) {
        List<Report> reports = carService.getAllReportsByGovId(govId);
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }

    public void downloadExcel(String govId) {
        List<Report> reports = carService.getAllReportsByGovId(govId);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Page1");
        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Title");
        headerRow.createCell(1).setCellValue("Detail");
        headerRow.createCell(2).setCellValue("LocalDateTime");
        for (Report report : reports) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(report.getTitle());
            row.createCell(1).setCellValue(report.getDetail());
            row.createCell(2).setCellValue(report.getLocalDateTime());
        }
        String filename = "C:\\Users\\azama\\OneDrive\\Документы\\AITU\\diploma\\reports\\report_" + govId + ".xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String govId) throws IOException {
        String inputFile = "C:\\Users\\azama\\OneDrive\\Документы\\AITU\\diploma\\reports\\report_" + govId + ".xlsx";
        File file = new File(inputFile);
        if (!file.exists()) file.delete();
        downloadExcel(govId);
        Path path = Paths.get(inputFile);
        byte[] fileContent = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", govId + ".xlsx");
        headers.setContentLength(fileContent.length);
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping("/download/pdf")
    public ResponseEntity<byte[]> downloadPdfFile(@RequestParam String govId) throws IOException {
        String inputFile = "C:\\Users\\azama\\OneDrive\\Документы\\AITU\\diploma\\reports\\report_" + govId + ".xlsx";
        String outPutFile = "C:\\Users\\azama\\OneDrive\\Документы\\AITU\\diploma\\reports\\report_pdf" + govId + ".pdf";
        File file = new File(inputFile);
        if (!file.exists()) file.delete();
        downloadExcel(govId);
        try {
            FileInputStream input = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(input);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outPutFile));
            document.open();

            XSSFSheet sheet = workbook.getSheetAt(0);
            PdfPTable table = new PdfPTable(sheet.getRow(0).getLastCellNum());
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                XSSFRow row = sheet.getRow(j);
                // Loop through each cell in the row
                for (int k = 0; k < row.getLastCellNum(); k++) {
                    XSSFCell cell = row.getCell(k);

                    // Add the cell value to the PDF table
                    if (cell != null) {
                        table.addCell(cell.toString());
                    }
                }
            }
            document.add(table);
            document.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Path path = Paths.get(outPutFile);
        byte[] fileContent = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", govId + ".pdf");
        headers.setContentLength(fileContent.length);
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
