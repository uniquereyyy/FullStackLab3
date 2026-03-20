package com.lab.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.lab.entity.Product;

public class MainApp {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure()
                .buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // INSERT
        Product p1 = new Product("Laptop", "Dell Laptop", 50000, 10);
        session.save(p1);

        // RETRIEVE
        Product p = session.get(Product.class, 1);
        System.out.println("Product Name: " + p.getName());

        // UPDATE
        p.setPrice(52000);
        session.update(p);

        // DELETE
        session.delete(p);

        tx.commit();

        session.close();
        factory.close();

        System.out.println("CRUD Operations Successful");
    }
}

