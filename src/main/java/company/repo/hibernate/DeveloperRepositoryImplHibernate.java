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
    private Developer developer;

    @Override
    public Developer create(Developer developer) {

        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(developer);
        transaction.commit();
        session.close();
        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(developer);

        transaction.commit();
        session.close();
        return developer;
    }

    @Override
    public void delete(Long aLong) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Developer developer = (Developer) session.get(Developer.class, aLong);
        session.delete(developer);
        transaction.commit();
        session.close();


    }

    @Override
    public Optional<Developer> read(Long aLong) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        developer = (Developer) session.get(Developer.class, aLong);
        transaction.commit();
        session.close();
        return Optional.ofNullable(developer);
    }

    @Override
    public Collection<Developer> getAll() {
        List<Developer> developerList = (List<Developer>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Developer ").list();
        return developerList;
    }
}
