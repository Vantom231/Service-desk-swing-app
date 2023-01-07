package dao;

import entities.MessagesArchive;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.sql.Timestamp;
import java.util.List;

public class MessagesArchiveDAO {

    public static List<MessagesArchive> getAllMessages(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<MessagesArchive> temp = null;

        try{
            Query query = session.createQuery("FROM MessagesArchive ");
            temp = query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }

        return temp;
    }

    public static boolean addMessage(int reports_id, Timestamp date,int sender, String message){
        Session session = HibernateUtils.getSessionFactory().openSession();
        MessagesArchive temp = new MessagesArchive();

        temp.setReportsId(reports_id);
        temp.setDate(date);
        temp.setSender(sender);
        temp.setMessage(message);

        try{
            session.getTransaction().begin();
            session.persist(temp);
            session.getTransaction().commit();
        }catch(HibernateException e){
//            System.err.println(e);
            return false;
        }finally {
            session.close();
        }
        return true;
    }
}
