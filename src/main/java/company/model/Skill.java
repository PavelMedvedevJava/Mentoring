package company.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Entity
@Table(name="skills",schema = "mydb")
public class Skill implements Serializable {
@Column(name = "skill")
    private String skill;
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
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
