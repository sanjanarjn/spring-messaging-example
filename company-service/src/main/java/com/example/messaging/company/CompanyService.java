package com.example.messaging.company;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public CompanyDto addCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setName(companyDto.getName());

        Company savedCompany = companyRepository.save(company);

        companyDto.setId(savedCompany.getId());
        return companyDto;
    }

    public List<CompanyDto> getAll() {

        List<CompanyDto> companyDtos = new ArrayList<>();
        List<Company> companies = companyRepository.findAll();
        for(Company company: companies) {
            companyDtos.add(modelMapper.map(company, CompanyDto.class));
        }
        return companyDtos;
    }

    public CompanyDto updateReviewInCompany(ReviewDto reviewDto) {
        Optional<Company> companyOptional = companyRepository.findById(reviewDto.getCompanyId());
        if(companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setRatingCount(company.getRatingCount() + 1);
            company.setAverageRating((company.getAverageRating() + reviewDto.getRating()) / company.getRatingCount());
            company = companyRepository.save(company);
            return modelMapper.map(company, CompanyDto.class);
        }

        throw new RuntimeException("Company id invalid");
    }
}
