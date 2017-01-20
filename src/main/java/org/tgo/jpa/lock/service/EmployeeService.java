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
				"Darwin", "Jônatas", "André", "Johnny", "Vinícius", "Jailton",
				"Charles", "Asafe", "Eloá", "Kemilly", "Daniela", "Conceição",
				"Felicity", "Suelen", "Jaiane", "Stephanie", "Belle", "Léia" };

		for (String name : names) {
			Employee employee = new Employee();
			employee.setName(name);
			entityManager.persist(employee);
		}

	}

	/**
	 * Le a entidade usando lock otimista. Irá checar o valor do versionamento quando a transação for comitada.
	 * Caso o numero do versionamento estaja igua (spoiler do teste abaixo: Não vai estar), transação é comitada.
	 * Caso seja diferente {@link OptimisticLockException} será lançado e a transação marcada para roll back
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
	 * Pelo meus teste, H2 (banco de dados usado pelo datasource padrão do Wildfly) não da suporte a esse tipo de lock.
	 * Para ver a magica acontecendo (e a diferença desse para o WRITE), use outro banco de dados
	 *
	 * Caso esteja usando o H2, o lock será promovido para PESSIMISTIC_WRITE e o erro acontecerá.
	 * Caso esteja usando outro banco de dados, esse caso funcionará.
	 *
	 */
	public Employee getEmployeePessimisticReadThrowError(int id) {
		Employee employee = entityManager.find(Employee.class, id, LockModeType.PESSIMISTIC_READ);
		service.readEmployee(id);
		return employee;
	}

	/**
	 * Após isso, ninguem le ou altera esse registro.
	 *
	 */
	public Employee getEmployeePessimisticWriteThrowError(int id) {
		Employee employee = entityManager.find(Employee.class, id, LockModeType.PESSIMISTIC_WRITE);
		service.readEmployee(id);
		return employee;
	}

}
