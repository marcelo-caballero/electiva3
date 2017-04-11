/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.*;

/**
 *
 * @author hugo
 */

public class ClienteManager {
    Session session = null;
    public String mess = "";
    int maxResults = 100;

    public ClienteManager()
    {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Cliente getCliente(Long id)
    {
        Cliente Cliente = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            Query q = session.createQuery("from Cliente as cliente where cliente.id=" + id);
            Cliente = (Cliente) q.uniqueResult();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }

        return Cliente;
    }

    public List getClientees(int pageSize, int page)
    {
        List<Cliente> clienteList = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery ("from Cliente cliente order by cliente.id");
            clienteList = (List<Cliente>) q.list().subList(page * pageSize, (page+1)*pageSize);
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }
        return clienteList;
    }

    public List getClientes()
    {
        List<Cliente> clienteList = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery ("from Cliente cliente order by cliente.id");
            clienteList = (List<Cliente>) q.list();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }
        return clienteList;
    }


    public List searchClientes(Cliente clienteSample)
    {

        List<Cliente> clienteList = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        org.hibernate.Transaction tx = session.beginTransaction();
        Criteria crit = session.createCriteria(Cliente.class);

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteSample.getNombre());
        cliente.setTelefono(clienteSample.getTelefono());
        cliente.setDireccion(clienteSample.getDireccion());
        cliente.setDocumento(clienteSample.getTelefono());
        cliente.setEmail(clienteSample.getEmail());

        Example example = Example.create(cliente).ignoreCase().excludeZeroes().enableLike(MatchMode.ANYWHERE);
        crit.add(example);
        if(clienteSample.getCiudad()!=null)
            crit.add(Restrictions.eq("ciudad", clienteSample.getCiudad()));
        crit.addOrder(Order.asc("id"));

        try {
            clienteList = (List<Cliente>) crit.list();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }
        return clienteList;
    }

    public boolean saveCliente(Cliente cliente)
    {
        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            session.saveOrUpdate(cliente);
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

    public boolean deleteCliente(Cliente cliente)
    {
        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            session.delete(cliente);
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
