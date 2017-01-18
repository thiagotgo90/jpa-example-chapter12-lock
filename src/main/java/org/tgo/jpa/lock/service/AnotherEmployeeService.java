package org.tgo.jpa.lock.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.tgo.jpa.lock.model.Employee;

@Stateless
public class AnotherEmployeeService {

	@PersistenceContext
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Employee getEmployee(int id) {

		Employee employee = entityManager.find(Employee.class, id);
		employee.setName("Willclass");

		return employee;
	}

}
