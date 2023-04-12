
package acme.features.assistant.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorials.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	//Internal	state ------------------------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	//AbstractService Interface -------------------------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Tutorial object;
		boolean status;
		final boolean isCreatorOfTutorial;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);
		isCreatorOfTutorial = super.getRequest().getPrincipal().getActiveRoleId() == object.getAssistant().getId();
		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status && isCreatorOfTutorial);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "summary", "goals", "draftMode", "course");
		tuple.put("readonly", true);

		super.getResponse().setData(tuple);
	}
}
