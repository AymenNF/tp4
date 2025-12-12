package uspace.infra.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uspace.UspaceMain;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;

public class HibernateCruiseRepository implements CruiseRepository {
    private final Logger logger = LoggerFactory.getLogger(UspaceMain.class);

    @Override
    public Cruise findById(CruiseId cruiseId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Cruise.class, cruiseId);
        } catch (Exception e) {
            logger.error("Error while trying to find cruise with id " + cruiseId, e);
            throw e;
        }
    }

    @Override
    public void save(Cruise cruise) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(cruise);

            transaction.commit();
        } catch (Exception e) {
            logger.error("Error while trying to save cruise with id " + cruise.getId(), e);

            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
