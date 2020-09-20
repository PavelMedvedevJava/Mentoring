package company.controller;

import company.model.Developer;
import company.repo.DeveloperRepository;
import company.repo.csv.DeveloperRepositoryImplCSV;
import company.repo.sql.DeveloperRepositoryImplSQL;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DeveloperController {

    private DeveloperRepository developerRepository;

    public DeveloperController(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public Developer create(Developer developer) {
        return developerRepository.create(developer);
    }

    public List<Developer> getAllDev() {
        return (List<Developer>) developerRepository.getAll();
    }


    public void deleteDeveloper(long id) {
        developerRepository.delete(id);
    }

    public Developer updateDeveloper(Developer developer) {
       return developerRepository.update(developer);
    }

    public Optional<Developer> getDeveloper(long id) {
        return Objects.requireNonNull(developerRepository.read(id));
    }

}
