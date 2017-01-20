package org.tgo.jpa.lock.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.tgo.jpa.lock.model.Employee;

@Stateless
public class EmployeeService {

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private CleaningFeeManagement service;

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

	/**
	 * Le a entidade usando lock otimista. Ir� checar o valor do versionamento quando a transa��o for comitada.
	 * Caso o numero do versionamento estaja igua (spoiler do teste abaixo: N�o vai estar), transa��o � comitada.
	 * Caso seja diferente {@link OptimisticLockException} ser� lan�ado e a transa��o marcada para roll back
	 *
	 */
	public Employee getEmployeeOptimisticReadThrowError(int id) {
		Employee employee = entityManager.find(Employee.class, id, LockModeType.OPTIMISTIC);
		service.changeEmployeeState(id);
		return employee;
	}


	/*
	 * O FORCE_INCREMENT esta na proxima camada.
	 */
	public Employee getEmployeeOptimisticWriteThrowError(int id) {
		Employee employee = entityManager.find(Employee.class, id, LockModeType.OPTIMISTIC);
		service.readEmployeeForceIncremet(id);
		return employee;
	}


	/**
	 * Pelo meus teste, H2 (banco de dados usado pelo datasource padr�o do Wildfly) n�o da suporte a esse tipo de lock.
	 * Para ver a magica acontecendo (e a diferen�a desse para o WRITE), use outro banco de dados
	 *
	 * Caso esteja usando o H2, o lock ser� promovido para PESSIMISTIC_WRITE e o erro acontecer�.
	 * Caso esteja usando outro banco de dados, esse caso funcionar�.
	 *
	 */
	public Employee getEmployeePessimisticReadThrowError(int id) {
		Employee employee = entityManager.find(Employee.class, id, LockModeType.PESSIMISTIC_READ);
		service.readEmployee(id);
		return employee;
	}

	/**
	 * Ap�s isso, ninguem le ou altera esse registro.
	 *
	 */
	public Employee getEmployeePessimisticWriteThrowError(int id) {
		Employee employee = entityManager.find(Employee.class, id, LockModeType.PESSIMISTIC_WRITE);
		service.readEmployee(id);
		return employee;
	}

}
