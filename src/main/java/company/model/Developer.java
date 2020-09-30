package company.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "developers", schema = "mydb")


public class Developer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "developer_skills",
            joinColumns = @JoinColumn(name="developer_id"),
    inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Developer(long id, String name, String lastName, Set<Skill> skills, Account account) {
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.skills = skills;
        this.account = account;
    }


    public Developer() {

    }

    public Developer(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Skill> getSkills() {
        return skills;
    }


    public void setSkill(Skill skill) {
        skills.add(skill);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer that = (Developer) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", skills=" + skills +
                ", account=" + account +
                '}';
    }
}
