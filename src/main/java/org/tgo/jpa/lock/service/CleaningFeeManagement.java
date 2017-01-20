package org.tgo.jpa.lock.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.tgo.jpa.lock.model.Employee;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CleaningFeeManagement {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Le o Emplooye e o altera. Numero de versionamento incrementado
	 */
	public Employee changeEmployeeState(int id) {
		Employee employee = entityManager.find(Employee.class, id/*, LockModeType.OPTIMISTIC_FORCE_INCREMENT*/);
		employee.setName("Willclass" + (int) (2000 * Math.random()));
		return employee;
	}


	/**
	 * Mesmo o Employee não sendo alterado, apenas de recupera-lo com essa forma de look,
	 * ja causara que seu numero de versionamento seja incrementado
	 *
	 */
	public Employee readEmployeeForceIncremet(int id) {
		Employee employee = entityManager.find(Employee.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		return employee;
	}


	/**
	 * Le o Emplooye e o altera. Numero de versionamento incrementado
	 */
	public Employee readEmployee(int id) {
		Employee employee = entityManager.find(Employee.class, id);
		return employee;
	}

}
