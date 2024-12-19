package org.example.ms.registry.ms.job;

import org.example.ms.registry.ms.job.external.Company;
import org.example.ms.registry.ms.job.external.Review;
import org.example.ms.registry.ms.job.integration.CompanyClient;
import org.example.ms.registry.ms.job.integration.ReviewClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${company.service.url}")
    private String companyServiceUrl;

    @Value("${review.service.url}")
    private String reviewServiceUrl;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    public Job createJob(Job job) {
        Job savedJob = jobRepository.save(job);
        return savedJob;
    }

    public JobWithCompanyDto getJobById(long id) throws JobNotFoundException {

        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {

            Job job = jobOptional.get();
            JobWithCompanyDto jobWithCompanyDto = getJobWithCompanyDto(job);
            return jobWithCompanyDto;

        } else throw new JobNotFoundException(MessageFormat.format("Job with id {0} not found", id));

    }

    private JobWithCompanyDto getJobWithCompanyDto(Job job) {
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        company.setReviews(reviews);

        JobWithCompanyDto jobWithCompanyDto = modelMapper.map(job, JobWithCompanyDto.class);
        jobWithCompanyDto.setCompany(company);

        return jobWithCompanyDto;
    }

    public List<JobWithCompanyDto> getAllJobs() {

        List<JobWithCompanyDto> jobsWithCompanies = new ArrayList<>();
        List<Job> jobs = jobRepository.findAll();
        for (Job job : jobs) {
            JobWithCompanyDto jobWithCompanyDto = getJobWithCompanyDto(job);
            jobsWithCompanies.add(jobWithCompanyDto);
        }
        return jobsWithCompanies;
    }

    public boolean deleteJobById(long id) throws JobNotFoundException {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            jobRepository.delete(job);
            return true;
        } else throw new JobNotFoundException(MessageFormat.format("Job with id {0} not found", id));

    }
}
