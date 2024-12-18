package org.example.ms.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${company.service.url}")
    private String companyServiceUrl;

    public Job createJob(Job job) {
        Job savedJob = jobRepository.save(job);
        return savedJob;
    }

    public Job getJobById(long id) throws JobNotFoundException {

        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent()) {
            return jobOptional.get();
        }
        else throw new JobNotFoundException(MessageFormat.format("Job with id {0} not found", id ));

    }

    public List<JobWithCompanyDto> getAllJobs() {

        List<JobWithCompanyDto> jobsWithCompanies = new ArrayList<>();
        List<Job> jobs = jobRepository.findAll();
        for(Job job: jobs) {
            String getCompanyByIdUrl = companyServiceUrl + "/" + job.getCompanyId();
            ResponseEntity companyResponse = restTemplate.getForEntity(getCompanyByIdUrl, Company.class);
            Company company = (Company) companyResponse.getBody();

            jobsWithCompanies.add(new JobWithCompanyDto(job, company));
        }
        return jobsWithCompanies;
    }

    public boolean deleteJobById(long id) throws JobNotFoundException {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent()) {
            Job job = jobOptional.get();
            jobRepository.delete(job);
            return true;
        }

        else throw new JobNotFoundException(MessageFormat.format("Job with id {0} not found", id ));

    }
}
