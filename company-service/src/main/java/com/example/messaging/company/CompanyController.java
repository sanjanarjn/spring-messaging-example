package com.example.messaging.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public void addCompany(@RequestBody CompanyDto companyDto) {
        companyService.addCompany(companyDto);
    }

    @GetMapping
    public List<CompanyDto> getCompanies() {
       return companyService.getAll();
    }
}
