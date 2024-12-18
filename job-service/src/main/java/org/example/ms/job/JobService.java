package org.example.ms.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

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

    public List<Job> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs;
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
