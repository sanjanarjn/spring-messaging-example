package com.example.ms.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public void addCompany(@RequestBody Company companyDto) {
        companyService.addCompany(companyDto);
    }

    @GetMapping
    public List<Company> getCompanies() {
       return companyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable long id) throws CompanyNotFoundException {
        Company company = companyService.getCompanyById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompanyById(@PathVariable long id) throws CompanyNotFoundException {
        boolean deleted = companyService.deleteCompanyById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
