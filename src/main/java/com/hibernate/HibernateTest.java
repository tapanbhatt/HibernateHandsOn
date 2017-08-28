package com.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateTest {

	/*public static void main(String[] args) {

		// Read
		System.out.println("******* READ *******");
		List<Employee> employees =list();
		System.out.println("Total Employees: " + employees.size());

		// Write
		System.out.println("******* WRITE *******");
		Employee empl = new Employee("Jack", "Bauer", new Date(System.currentTimeMillis()), "911");
		empl = save(empl);
		empl = read(empl.getId());
		System.out.printf("%d %s %s \n", empl.getId(), empl.getFirstname(),empl.getLastname());

		// Update
		System.out.println("******* UPDATE *******");
		Employee empl2 = read(1l); // read employee with id 1
		System.out.println("Name Before Update:" + empl2.getFirstname());
		empl2.setFirstname("James");
		update(empl2); // save the updated employee details

		empl2 = read(1l); // read again employee with id 1
		System.out.println("Name Aftere Update:" + empl2.getFirstname());

		// Delete
		System.out.println("******* DELETE *******");
		delete(empl);
		Employee empl3 = read(empl.getId());
		System.out.println("Object:" + empl3);
	}*/
	
	

	public static void main(String[] args) {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		// Update employee without loading the object from DB:
		// Prob with this approach: You need to set all the properties of an object.
		// If you dont set all the default it set to null
		
		Employee employee = new Employee();
		employee.setId(new Long(1));
		employee.setFirstname("Tapan");
		employee.setLastname("Bhatt");
		employee.setBirthDate(new Date("04/09/1981"));
		employee.setCellphone("9828027654");
		session.save(employee);
		transaction.commit();
		session.close();
		
		/*// Update employee,loading the object from DB using get Method:
		Employee employee = (Employee)session.get(Employee.class, new Long(1));
		employee.setId(new Long(1));
		employee.setFirstname("Suraj");
		employee.setLastname("Bhatt");
		employee.setBirthDate(new Date("02/10/1983"));
		employee.setCellphone("9828027684");
		session.update(employee);
		transaction.commit();
		session.close();*/
		
		// Update employee,loading the object from DB using get Method:
		// Load() method also can be used in update in this we no need to set 
		
		/*Employee employee = (Employee)session.get(Employee.class, new Long(1));
		session.close();
		
		employee.setFirstname("Suraj");

		// Re-attaching the object to the session
		Session session2 = sessionFactory.openSession();		
        Employee employee2 = (Employee)session2.get(Employee.class, new Long(1));		
        Transaction transaction = session2.beginTransaction();
		
        //When we call update() method, if the object already existed in cache update() method
        //will throw exception where as merge() method copies the changes in to cache.
		session2.update(employee);

		//If we call merge() method, then it verifies whether the same object is existed in the cache or not. 
		//If the object is existed in the cache then changes are copied in to the cache. other wise it will load the values to cache.
		//Hence it doesn’t throw any exception.
		
		//session2.merge(employee);
		
		transaction.commit();
		session2.close();*/
		session.close();
		sessionFactory.close();
		
		
		
		
	}
	
	
	private static List<Employee> list() {
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		List<Employee> employees = session.createQuery("from Employee").list();
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
