package company.repo.hibernate;

import company.model.Account;
import company.model.AccountStatus;
import company.model.Skill;
import company.repo.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static company.repo.hibernate.HibernateSessionFactoryUtil.getSessionFactory;

public class AccountRepositoryImplHibernate implements AccountRepository {
    private Account account;
    private Session session;

    @Override
    public Account create(Account account) {
        session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(account);
        transaction.commit();
        session.close();
        return account;
    }

    @Override
    public Account update(Account account) {
        session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(account);
        transaction.commit();
        session.close();
        return account;
    }

    @Override
    public void delete(Long aLong) {
        session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        account = (Account) session.get(Account.class, aLong);
        session.delete(account);
        transaction.commit();
        session.close();
    }

    @Override
    public Optional<Account> read(Long aLong) {

        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        account = (Account) session.get(Account.class, aLong);
        session.close();
        transaction.commit();
        return Optional.ofNullable(account);

    }

    @Override
    public Collection<Account> getAll() {
        List<Account> accountList = (List<Account>) getSessionFactory().openSession().createQuery("From Account").list();
        return accountList;
    }
}
