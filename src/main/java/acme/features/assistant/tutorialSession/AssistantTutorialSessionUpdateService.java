
package acme.features.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.NatureType.NatureType;
import acme.entities.tutorialSessions.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionUpdateService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		TutorialSession tutorialSession;

		id = super.getRequest().getData("id", int.class);
		tutorialSession = this.repository.findOneTutorialSessionById(id);
		status = tutorialSession != null && tutorialSession.isDraftMode() && super.getRequest().getPrincipal().getActiveRoleId() == tutorialSession.getTutorial().getAssistant().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		TutorialSession tutorialSession;

		id = super.getRequest().getData("id", int.class);
		tutorialSession = this.repository.findOneTutorialSessionById(id);

		super.getBuffer().setData(tutorialSession);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;

		super.bind(object, "title", "type", "summary", "startDate", "finishDate", "link", "draftMode");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;
	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(NatureType.class, object.getType());

		tuple = super.unbind(object, "title", "type", "summary", "startDate", "finishDate", "link", "draftMode");

		tuple.put("types", choices);
		tuple.put("masterId", object.getTutorial().getId());

		super.getResponse().setData(tuple);
	}

}
