package ua.nmu.smahin.spring.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nmu.smahin.spring.services.ExcelService;

import java.io.IOException;

@Controller
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @GetMapping
    public void excelReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report.xlsx";
        response.setHeader(headerKey, headerValue);
        excelService.getReport(response);
    }
}
