package com.example.ms.company;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public Company addCompany(Company company) {

        Company savedCompany = companyRepository.save(company);
        return savedCompany;
    }

    public List<Company> getAll() {

        List<Company> companies = companyRepository.findAll();
        return companies;
    }

    public Company updateReviewInCompany(ReviewMessage reviewMessage) {
        Optional<Company> companyOptional = companyRepository.findById(reviewMessage.getCompanyId());
        if(companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setRatingCount(company.getRatingCount() + 1);
            company.setAverageRating((company.getAverageRating() + reviewMessage.getRating()) / company.getRatingCount());
            company = companyRepository.save(company);
            return company;
        }
        throw new RuntimeException("Company id invalid");
    }

    public Company getCompanyById(long id) throws CompanyNotFoundException {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()) {
            return companyOptional.get();
        }
        else throw new CompanyNotFoundException(MessageFormat.format("Company with id {0} not found", id ));
    }

    public boolean deleteCompanyById(long id) throws CompanyNotFoundException {

        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()) {
            Company company = companyOptional.get();
            companyRepository.delete(company);
            return true;
        }

        else throw new CompanyNotFoundException(MessageFormat.format("Job with id {0} not found", id ));

    }
}
