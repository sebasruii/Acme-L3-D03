
package acme.features.authenticated.lecturer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class AuthenticatedLecturerController extends AbstractController<Authenticated, Lecturer> {

	@Autowired
	AuthenticatedLecturerCreateService createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
	}
}
