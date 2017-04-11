/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.*;

/**
 *
 * @author hugo
 */

public class CiudadManager {
    Session session = null;
    public String mess = "";
    int maxResults = 100;

    public CiudadManager()
    {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Ciudad getCiudad(Long id)
    {
        Ciudad Ciudad = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            Query q = session.createQuery("from Ciudad as ciudad where ciudad.id=" + id);
            Ciudad = (Ciudad) q.uniqueResult();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }

        return Ciudad;
    }

    public List getCiudades(int pageSize, int page)
    {
        List<Ciudad> ciudadList = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery ("from Ciudad ciudad order by ciudad.id");
            ciudadList = (List<Ciudad>) q.list().subList(page * pageSize, (page+1)*pageSize);
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }
        return ciudadList;
    }

    public List getCiudades()
    {
        List<Ciudad> ciudadList = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery ("from Ciudad ciudad order by ciudad.id");
            ciudadList = (List<Ciudad>) q.list();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }
        return ciudadList;
    }


    public List searchCiudades(Ciudad ciudadSample)
    {
        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        List<Ciudad> ciudadList = null;
        Example example = Example.create(ciudadSample).ignoreCase().excludeZeroes().enableLike(MatchMode.START);

        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            ciudadList = (List<Ciudad>) session.createCriteria(Ciudad.class).
                    add(example).addOrder(Order.asc("id")).list();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }
        return ciudadList;
    }

    public boolean saveCiudad(Ciudad ciudad)
    {
        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            session.saveOrUpdate(ciudad);
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteCiudad(Ciudad ciudad)
    {
        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            session.delete(ciudad);
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
            return false;
        }
        return true;
    }


}
