package com.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CrudOperation {

	private static Employee save(Employee employee) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		Long id = (Long) session.save(employee);
		employee.setId(id);

		session.getTransaction().commit();

		session.close();

		return employee;
	}

	private static List list() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		List employees = session.createQuery("from Employee").list();
		session.close();
		return employees;
	}

	private static Employee read(Long id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Employee employee = (Employee) session.get(Employee.class, id);
		session.close();
		return employee;
	}

	private static Employee update(Employee employee) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		session.merge(employee);

		session.getTransaction().commit();

		session.close();
		return employee;

	}

	private static void delete(Employee employee) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();

		session.delete(employee);

		session.getTransaction().commit();

		session.close();
	}

}
