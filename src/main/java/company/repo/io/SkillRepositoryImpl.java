package company.repo.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import company.model.Skill;
import company.repo.SkillRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SkillRepositoryImpl implements SkillRepository {

    private long idCounter = 1;

    private Path filePath = Paths.get("dz\\src\\main\\resources\\skills.json");

    private final String fileName = "dz\\src\\main\\resources\\skills.json";

    private List<Skill> listOfSkills = new ArrayList<>();


    private List<Skill> getAllSkill() {
        if (!Files.exists(filePath)) {

            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        Reader reader = null;
        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson1 = new GsonBuilder().create();
        listOfSkills = gson1.fromJson(reader, new TypeToken<ArrayList<Skill>>() {
        }.getType());
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
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(listOfSkills, writer);
        } catch (IOException e) {
            System.out.println("Error write file");
        }

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

        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(listOfSkills, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private Long getNewId() {
       return getAllSkill().stream().max(Comparator.comparing(i -> i.getId())).get().getId() + 1;
    }


}

