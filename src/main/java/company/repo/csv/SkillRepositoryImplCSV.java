package company.repo.csv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import company.model.Skill;
import company.repo.SkillRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SkillRepositoryImplCSV  implements SkillRepository {
    private long idCounter = 1;

    private Path filePath = Paths.get("\\git\\mentoring\\src\\main\\resources\\skills.csv");

    private final String fileName = "\\git\\mentoring\\src\\main\\resources\\skills.csv";

    private List<Skill> listOfSkills = new ArrayList<>();


    private List<Skill> getAllSkill() {
        if (!Files.exists(filePath)) {

            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            Reader reader = Files.newBufferedReader(filePath);

            listOfSkills = new CsvToBeanBuilder(reader).withType(Skill.class).build().parse();
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return listOfSkills;
    }

    @Override
    public Skill create(Skill skill) {

        if (Files.exists(filePath)) {

            try {
                if (Files.size(filePath) > 5) {
                    idCounter = getNewId();
                    skill.setId(idCounter);
                    listOfSkills.add(skill);
                } else {
                    skill.setId(idCounter);
                    idCounter++;
                    listOfSkills.add(skill);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            skill.setId(idCounter);
            idCounter++;
            listOfSkills.add(skill);

        }

       writeSkillList(listOfSkills);

        return skill;
    }

    @Override
    public List<Skill> getAll() {
        return getAllSkill();
    }

    @Override
    public Skill update(Skill skill) {
        delete(skill.getId());

        listOfSkills.add(skill);

       writeSkillList(listOfSkills);
        return skill;

    }

    @Override
    public void delete(Long id) {

        listOfSkills.removeIf(e -> e.getId() == id);

        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(listOfSkills, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Optional<Skill> read(Long id) {
        return Objects.requireNonNull(getAllSkill().stream().filter(x -> x.getId() == id).findFirst());

    }
    private void writeSkillList(List listOfSkills) {
        try {
            Writer writer = Files.newBufferedWriter(filePath);
            StatefulBeanToCsv<Skill> csvWriter = new StatefulBeanToCsvBuilder<Skill>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(false)
                    .build();
            csvWriter.write(listOfSkills);
            writer.close();

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }

    }

    private Long getNewId() {
        return getAllSkill().stream().max(Comparator.comparing(i -> i.getId())).get().getId() + 1;
    }
}
