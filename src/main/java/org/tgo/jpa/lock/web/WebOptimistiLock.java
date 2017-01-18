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

@Path("/optimistic")
public class WebOptimistiLock {

	@EJB
	private EmployeeService service;

	@GET
	@Path("/employee/")
	public Response populate() {
		try {
			service.populate();
			return Response.ok().build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}

	}

	@GET
	@Path("/employee/{id}")
	@Produces({ "application/json" })
	public Response getEmployee(@PathParam("id") int id) {
		Employee employee = service.getEmployee(id);
		return Response.ok(employee, MediaType.APPLICATION_JSON).build();
	}

}
