package company.repo.io;


import company.model.Developer;
import company.model.Skill;
import company.repo.AccountRepository;
import company.repo.DeveloperRepository;
import company.repo.SkillRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    private long idCounter = 1;

    private Path filePath = Paths.get("\\git\\mentoring\\src\\main\\resources\\developer.json");


    private List<Developer> developers;

    private SkillRepository skillRepository = new SkillRepositoryImpl();

    private AccountRepository accountRepository = new AccountRepositoryImpl();

    private Developer developer;

    private void fileCheck() {

        if (!Files.exists(filePath)) {

            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private List<Developer> getAllDeveloper() {

       fileCheck();

        return createToDeveloperList(readFile());

    }

    @Override
    public Developer create(Developer developer) {
        if (!Files.exists(filePath)) {

            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


            try {
                if (Files.size(filePath) > 5) {
                    idCounter = getNewId();
                    developer.setId(idCounter);
                } else {
                    developer.setId(idCounter);
                    idCounter++;

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        writeOneDeveloper(developerToString(developer));



        return developer;

    }

    @Override
    public Developer update(Developer developer) {

        delete(developer.getId());

        developers = getAllDeveloper();

        developers.add(developer);

        writeAllDeveloper(createToStringList(developers));

        return developer;
    }


    @Override
    public void delete(Long id) {
        developers = getAllDeveloper();

        developers.removeIf(e -> e.getId() == id);

        writeAllDeveloper(createToStringList(developers));

    }


    @Override
    public List<Developer> getAll() {

        return getAllDeveloper();
    }

    @Override
    public Optional<Developer> read(Long id) {
        return Objects.requireNonNull(getAllDeveloper()).stream().filter(x -> x.getId() == id).findFirst();
    }


    private Long getNewId() {
        return getAllDeveloper().stream().max(Comparator.comparing(i -> i.getId())).get().getId() + 1;
    }

    private String developerToString(Developer developer) {
        System.out.println(developer);
        StringBuilder str = new StringBuilder();
            str.append( developer.getName() + "-" + developer.getLastName() + "-"+developer.getId()+"-" + developer.getAccount().getId() + "-");

        for (Skill sk : developer.getSkills()) {

            str.append(sk.getId() + "-");
        }

        str.append('\n');

        return str.toString();

    }

    private List<String> createToStringList(List<Developer> listDevelopers) {

        List<String> stringList = new ArrayList<>();

        listDevelopers.forEach(x->stringList.add(developerToString(x)));

        return stringList;

    }


    private List<Developer> createToDeveloperList(List<String> stringList) {

        List<Developer> developerList = new ArrayList<>();

        stringList.forEach(x -> developerList.add(getDeveloper(x)));

        return developerList;
    }

    private List<String> readFile() {

        List<String> stringList = new ArrayList<>();

        fileCheck();

            try {
                stringList = Files.readAllLines(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return stringList;
    }

    private void writeOneDeveloper(String developerString) {
        try {
            Files.write(filePath, developerString.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAllDeveloper(List<String> stringList) {

        try {
            Files.newBufferedWriter(filePath , StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringList.forEach(x -> {
            try {
                Files.write(filePath, x.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private Developer getDeveloper(String stringDeveloper) {
        String[] subStr;

        String delimiter = "-";

        subStr = stringDeveloper.split(delimiter);

        developer = new Developer();

        for (int i = 0; i < subStr.length; i++) {

            if (i == 0) {
                developer.setName(subStr[i]);

            }
            if (i == 1) {
                developer.setLastName(subStr[i]);

            }
            if (i == 2) {
                developer.setId(Long.parseLong(subStr[i]));
            }
                if (i == 3) {
                developer.setAccount(accountRepository.read(Long.valueOf(subStr[i])).get());

            } else if(i>3) {

                developer.setSkill(skillRepository.read(Long.valueOf(subStr[i])).get());
            }
        }


        return developer;
    }



}
