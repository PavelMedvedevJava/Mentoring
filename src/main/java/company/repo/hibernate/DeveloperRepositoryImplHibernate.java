package company.repo.hibernate;

import company.model.Developer;
import company.model.Skill;
import company.repo.DeveloperRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DeveloperRepositoryImplHibernate implements DeveloperRepository {
    private Session session;
    @Override
    public Developer create(Developer developer) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(developer);
        tx1.commit();
        session.close();
        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(developer);

        tx1.commit();
        session.close();
        return developer;
    }

    @Override
    public void delete(Long aLong) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(String.valueOf(Developer.class),aLong);
        tx1.commit();
        session.close();


    }

    @Override
    public Optional<Developer> read(Long aLong) {
        return (Optional<Developer>) HibernateSessionFactoryUtil.getSessionFactory().openSession().get(String.valueOf(Developer.class), aLong);
    }

    @Override
    public Collection<Developer> getAll() {
        List<Developer> developerList = (List<Developer>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Developer ").list();
        return developerList;
    }
}
