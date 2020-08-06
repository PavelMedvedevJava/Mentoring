package company.controller;

import company.model.Developer;
import company.repo.DeveloperRepository;
import company.repo.io.DeveloperRepositoryImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DeveloperController {

    private DeveloperRepository developerRepository = new DeveloperRepositoryImpl();

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
