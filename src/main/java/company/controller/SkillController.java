package company.controller;

import company.model.Skill;
import company.repo.SkillRepository;
import company.repo.csv.SkillRepositoryImplCSV;
import company.repo.sql.SkillRepositoryImplSQL;

import java.util.List;

public class SkillController {


    private SkillRepository skillRepository ;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkill() {
        return (List<Skill>) skillRepository.getAll();
    }

    public Skill addSkill(Skill skill ) {
        return skillRepository.create(skill);
    }

    public void deleteSkill(long id) {
        skillRepository.delete(id);
    }

    public Skill updateSkill(Skill skill) {
       return skillRepository.update(skill);
    }

}

