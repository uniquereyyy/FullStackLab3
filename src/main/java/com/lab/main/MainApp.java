package com.lab.main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.lab.entity.Product;

public class MainApp {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();

        // 🔹 Insert multiple records
        session.save(new Product("Phone", "Electronics", 20000, 5));
        session.save(new Product("Laptop", "Electronics", 50000, 10));
        session.save(new Product("Shirt", "Clothing", 1500, 20));
        session.save(new Product("Shoes", "Footwear", 3000, 15));
        session.save(new Product("Watch", "Accessories", 2500, 8));
        session.save(new Product("Bag", "Accessories", 1800, 12));

        // 🔹 Sorting (Ascending)
        Query<Product> q1 = session.createQuery("from Product order by price asc", Product.class);
        List<Product> list1 = q1.getResultList();
        System.out.println("Ascending Order:");
        list1.forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));

        // 🔹 Sorting (Descending)
        Query<Product> q2 = session.createQuery("from Product order by price desc", Product.class);
        System.out.println("\nDescending Order:");
        q2.getResultList().forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));

        // 🔹 Sort by quantity
        Query<Product> q3 = session.createQuery("from Product order by quantity desc", Product.class);
        System.out.println("\nSort by Quantity:");
        q3.getResultList().forEach(p -> System.out.println(p.getName() + " " + p.getQuantity()));

        // 🔹 Pagination
        Query<Product> q4 = session.createQuery("from Product", Product.class);
        q4.setFirstResult(0);
        q4.setMaxResults(3);
        System.out.println("\nFirst 3 Products:");
        q4.getResultList().forEach(p -> System.out.println(p.getName()));

        q4.setFirstResult(3);
        System.out.println("\nNext 3 Products:");
        q4.getResultList().forEach(p -> System.out.println(p.getName()));

        // 🔹 Aggregate functions
        Long count = (Long) session.createQuery("select count(*) from Product").uniqueResult();
        System.out.println("\nTotal Products: " + count);

        List<Object[]> group = session.createQuery(
                "select description, count(*) from Product group by description").getResultList();

        System.out.println("\nGroup By Description:");
        for (Object[] obj : group) {
            System.out.println(obj[0] + " -> " + obj[1]);
        }

        Object[] minMax = (Object[]) session.createQuery(
                "select min(price), max(price) from Product").uniqueResult();

        System.out.println("\nMin Price: " + minMax[0]);
        System.out.println("Max Price: " + minMax[1]);

        // 🔹 WHERE clause
        Query<Product> q5 = session.createQuery(
                "from Product where price between 1000 and 30000", Product.class);

        System.out.println("\nPrice between 1000 and 30000:");
        q5.getResultList().forEach(p -> System.out.println(p.getName()));

        // 🔹 LIKE queries
        Query<Product> q6 = session.createQuery(
                "from Product where name like 'P%'", Product.class);

        System.out.println("\nNames starting with P:");
        q6.getResultList().forEach(p -> System.out.println(p.getName()));

        session.getTransaction().commit();
        session.close();
        factory.close();
    }
}

