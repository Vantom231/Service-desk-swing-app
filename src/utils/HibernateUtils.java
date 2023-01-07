package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;

import java.math.BigInteger;

public class HibernateUtils {
    private static final Configuration config = new Configuration();
    private static SessionFactory sessionFactory =null;


    public static boolean connecToDB(){
        try{
            sessionFactory =  config.configure("resources/hibernate.cfg.xml").buildSessionFactory();
            return true;
        }catch(ServiceException e){
            return false;
        }

    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public static int getLastID(){
        Session session = getSessionFactory().openSession();
        BigInteger lastId = (BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult();
        session.close();
        return lastId.intValue();
    }
}
