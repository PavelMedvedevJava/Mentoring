package company.repo.hibernate;

import company.model.Account;
import company.model.Skill;
import company.repo.SkillRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SkillRepositoryImplHibernate implements SkillRepository {
   private Session session;
    @Override
    public Skill create(Skill skill) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(skill);
        tx1.commit();
        session.close();
        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(skill);

        tx1.commit();
        session.close();
        return skill;
    }

    @Override
    public void delete(Long aLong) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(String.valueOf(Skill.class),aLong);
        tx1.commit();
        session.close();

    }

    @Override
    public Optional<Skill> read(Long aLong) {
        return (Optional<Skill>) HibernateSessionFactoryUtil.getSessionFactory().openSession().get(String.valueOf(Skill.class), aLong);
    }

    @Override
    public Collection<Skill> getAll() {
        List<Skill> skillList = (List<Skill>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Skill ").list();
        return skillList;
    }
}
