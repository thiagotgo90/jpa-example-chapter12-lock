package org.tgo.jpa.lock.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.tgo.jpa.lock.model.Employee;

@Stateless
public class EmployeeService {

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private AnotherEmployeeService service;

	public void populate() {

		String[] names = new String[] { "Thiago", "Gilvan", "Rodrigo", "Ivan",
				"Darwin", "J�natas", "Andr�", "Johnny", "Vin�cius", "Jailton",
				"Charles", "Asafe", "Elo�", "Kemilly", "Daniela", "Concei��o",
				"Felicity", "Suelen", "Jaiane", "Stephanie", "Belle", "L�ia" };

		for (String name : names) {
			Employee employee = new Employee();
			employee.setName(name);
			entityManager.persist(employee);
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee getEmployee(int id) {
		Employee employee = entityManager.find(Employee.class, id/*, LockModeType.OPTIMISTIC*/);

		service.getEmployee(id);

		return employee;
	}

}
