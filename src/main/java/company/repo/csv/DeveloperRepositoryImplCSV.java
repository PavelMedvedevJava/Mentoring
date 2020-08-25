package company.repo.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import company.model.Developer;
import company.model.Skill;
import company.repo.AccountRepository;
import company.repo.DeveloperRepository;
import company.repo.SkillRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class DeveloperRepositoryImplCSV implements DeveloperRepository {
    private long idCounter = 1;

    private Path filePath = Paths.get("\\git\\mentoring\\src\\main\\resources\\developer.csv");


    private List<Developer> developers;

    private SkillRepository skillRepository = new SkillRepositoryImplCSV();

    private AccountRepository accountRepository = new AccountRepositoryImplCSV();

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
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createToDeveloperList(readAll( reader));


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
        developers = getAllDeveloper();
        developers.add(developer);
        try {
            csvWriterAll(createToStringList(developers));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return developer;

    }

    @Override
    public Developer update(Developer developer) {

        delete(developer.getId());

        developers = getAllDeveloper();

        developers.add(developer);

        try {
            csvWriterAll(createToStringList(developers));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return developer;
    }


    @Override
    public void delete(Long id) {
        developers = getAllDeveloper();

        developers.removeIf(e -> e.getId() == id);

        try {
            csvWriterAll(createToStringList(developers));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private String[] developerToString(Developer developer) {
        System.out.println(developer);
        StringBuilder str = new StringBuilder();
        String[] developerString = new String[5];
        developerString[0] = developer.getName();
        developerString[1] = developer.getLastName();
        developerString[2] = String.valueOf(developer.getId());
        developerString[3] = String.valueOf(developer.getAccount().getId());
        int count = 1;
            for (Skill sk : developer.getSkills()) {
                str.append(sk.getId());
                if (developer.getSkills().size() != 1&&count!=developer.getSkills().size()) {
                    str.append( ";");
                }
                count++;
            }

        developerString[4] = str.toString();

        return developerString;

    }

    private List<String[]> createToStringList(List<Developer> listDevelopers) {

        List<String[]> stringList = new ArrayList<>();

        listDevelopers.forEach(x->stringList.add(developerToString(x)));

        return stringList;

    }


    private List<Developer> createToDeveloperList(List<String[]> stringList) {

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

    private Developer getDeveloper(String[] stringDeveloper) {
        String[] subStr;
        String delimiter = ";";

        developer = new Developer();

        developer.setName(stringDeveloper[0]);
        developer.setLastName(stringDeveloper[1]);
        developer.setId(Long.parseLong(stringDeveloper[2]));
        developer.setAccount(accountRepository.read(Long.valueOf(stringDeveloper[3])).get());
        if (stringDeveloper[4].length() != 1) {
            subStr = stringDeveloper[4].split(delimiter);
            System.out.println(subStr.length);
            for (int i=0;i<subStr.length;i++ ) {
                developer.setSkill(skillRepository.read(Long.valueOf(subStr[i])).get());
            }

        } else {
            developer.setSkill(skillRepository.read(Long.valueOf(stringDeveloper[4])).get());
        }





        return developer;
    }
    public List<String[]> readAll(Reader reader) {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        try {
            list = csvReader.readAll();
            reader.close();
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void csvWriterAll(List<String[]> stringArray) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(filePath.toString()));

        writer.writeAll(stringArray);
        writer.close();

    }
}
