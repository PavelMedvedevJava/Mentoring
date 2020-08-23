package company.model;

import java.io.Serializable;
import java.util.Objects;

public class Skill implements Serializable {

    private String skill;

    private long id;

    public Skill(String skill) {

        this.skill = skill;

    }

    public Skill(String skill, long id) {
        this.skill = skill;
        this.id = id;

    }

    public Skill() {
    }

    public String getSkill() {
        return skill;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id == skill.id &&
                Objects.equals(this.skill, skill.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, id);
    }

    @Override
    public String toString() {
        return id + " " + skill;
    }
}
