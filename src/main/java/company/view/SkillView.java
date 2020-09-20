package company.view;

import company.controller.SkillController;
import company.model.Skill;
import company.repo.sql.SkillRepositoryImplSQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class SkillView {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private SkillController skillController = new SkillController(new SkillRepositoryImplSQL());

    public Skill addNewSkill() {

        String skillStr = "";

            while (!false) {


                System.out.println("Write skill developer ");
                try {
                    skillStr = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (!(skillStr.length() <= 2) && !(skillStr.length() <= 2)) {
                    return skillController.addSkill(new Skill(skillStr));
                }
            }

    }

    public long deleteSkill(Set<Skill> skillSet) {
        skillSet.forEach(System.out::println);
        while (true) {
            System.out.println("Please indicate the skill you want to delete");
            try {
                long idForDelete = Integer.parseInt(reader.readLine());
                System.out.println(idForDelete);
                if ((skillSet.stream().filter(x -> x.getId() == idForDelete).findFirst().orElse(null)) == null) {
                    System.out.println("This skill does not exist please enter again");
                } else {
                    skillController.deleteSkill(idForDelete);
                    return idForDelete;
                }

            } catch (Exception e) {
            }
        }
    }

    public Skill createSkill(Skill skill)  {

        String newSkill = "";

        while (true) {

            System.out.println("Write new skill developer");

            try {
                newSkill = reader.readLine();
                if (newSkill != "") {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
skill.setSkill(newSkill);
       return skillController.updateSkill(skill);
    }
}

