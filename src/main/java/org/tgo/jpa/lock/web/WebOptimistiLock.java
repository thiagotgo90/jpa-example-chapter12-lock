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

@Path("/optimistic/employee/")
public class WebOptimistiLock {

	@EJB
	private EmployeeService service;

	@GET
	@Path("/")
	public Response populate() {
		try {
			service.populate();
			return Response.ok("database populado", MediaType.APPLICATION_JSON).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}

	}

	@GET
	@Path("/read/{id}")
	@Produces({ "application/json" })
	public Response optimistcRead(@PathParam("id") int id) {
		Employee employee = service.getEmployeeOptimisticReadThrowError(id);
		return Response.ok(employee, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/write/{id}")
	@Produces({ "application/json" })
	public Response optimistcWrite(@PathParam("id") int id) {
		Employee employee = service.getEmployeeOptimisticWriteThrowError(id);
		return Response.ok(employee, MediaType.APPLICATION_JSON).build();
	}

}
