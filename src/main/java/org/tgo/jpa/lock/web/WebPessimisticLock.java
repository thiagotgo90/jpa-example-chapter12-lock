package org.tgo.jpa.lock.web;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.tgo.jpa.lock.model.Employee;
import org.tgo.jpa.lock.service.EmployeeService;

@Path("/pessimistic/employee/")
public class WebPessimisticLock {

	@EJB
	private EmployeeService service;


	@GET
	@Path("/read/{id}")
	@Produces({ "application/json" })
	public Response optimistcRead(@PathParam("id") int id) {
		Employee employee = service.getEmployeePessimisticReadThrowError(id);
		return Response.ok(employee, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/write/{id}")
	@Produces({ "application/json" })
	public Response optimistcWrite(@PathParam("id") int id) {
		Employee employee = service.getEmployeePessimisticWriteThrowError(id);
		return Response.ok(employee, MediaType.APPLICATION_JSON).build();
	}

}
