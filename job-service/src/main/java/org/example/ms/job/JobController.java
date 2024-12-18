package org.example.ms.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<?> getAllJobs() throws JobNotFoundException {
        List<JobWithCompanyDto> job = jobService.getAllJobs();
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody Job job) {
        Job savedJob = jobService.createJob(job);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable long id) throws JobNotFoundException {
        Job job = jobService.getJobById(id);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobById(@PathVariable long id) throws JobNotFoundException {
        boolean deleted = jobService.deleteJobById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
