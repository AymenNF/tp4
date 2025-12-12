package uspace.infra.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uspace.UspaceMain;
import uspace.domain.account.Account;
import uspace.domain.account.AccountRepository;
import uspace.domain.account.AccountUsername;

public class HibernateAccountRepository implements AccountRepository {
    private final Logger logger = LoggerFactory.getLogger(UspaceMain.class);

    @Override
    public Account findByUsername(AccountUsername username) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Account.class, username);
        } catch (Exception e) {
            logger.error("Error while trying to find account with username " + username, e);
            throw e;
        }
    }

    @Override
    public void save(Account account) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(account);

            transaction.commit();
        } catch (Exception e) {
            logger.error("Error while trying to save account with username " + account.getUsername(), e);

            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(Account account) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(account);

            transaction.commit();
        } catch (Exception e) {
            logger.error("Error while trying to delete account with username " + account.getUsername(), e);

            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
