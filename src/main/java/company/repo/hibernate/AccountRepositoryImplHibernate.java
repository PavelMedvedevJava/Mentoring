package company.repo.hibernate;

import company.model.Account;
import company.repo.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImplHibernate implements AccountRepository {
    SessionFactory sessionFactory;
   private Session session ;
    @Override
    public Account create(Account account) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(account);
        tx1.commit();
        session.close();
        return account;
    }

    @Override
    public Account update(Account account) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(account);

        tx1.commit();
        session.close();
        return account;
    }

    @Override
    public void delete(Long aLong) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(String.valueOf(Account.class),aLong);
        tx1.commit();
        session.close();
    }

    @Override
    public Optional<Account> read(Long aLong) {
         return (Optional<Account>) HibernateSessionFactoryUtil.getSessionFactory().openSession().get(String.valueOf(Account.class), aLong);
    }

    @Override
    public Collection<Account> getAll() {
        List<Account> accountList = (List<Account>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Account").list();
        return accountList;
    }
}
