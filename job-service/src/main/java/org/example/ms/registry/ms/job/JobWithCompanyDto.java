package org.example.ms.registry.ms.job;

import org.example.ms.registry.ms.job.external.Company;

public final class JobWithCompanyDto extends Job {

    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
