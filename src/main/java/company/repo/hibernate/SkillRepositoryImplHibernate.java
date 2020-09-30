package company.repo.hibernate;

import company.model.Account;
import company.model.Developer;
import company.model.Skill;
import company.repo.SkillRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SkillRepositoryImplHibernate implements SkillRepository {
   private Session session;
    private Skill skill;
    @Override
    public Skill create(Skill skill) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(skill);
        transaction.commit();
        session.close();
        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(skill);
        transaction.commit();
        session.close();
        return skill;
    }

    @Override
    public void delete(Long aLong) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        skill= (Skill) session.get(Skill.class, aLong);
        session.delete(skill);
        transaction.commit();
        session.close();

    }

    @Override
    public Optional<Skill> read(Long aLong) {

        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        skill= (Skill) session.get(Skill.class, aLong);
        transaction.commit();
        session.close();
        return Optional.ofNullable(skill);
    }

    @Override
    public Collection<Skill> getAll() {
        List<Skill> skillList = (List<Skill>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Skill ").list();
        return skillList;
    }
}
