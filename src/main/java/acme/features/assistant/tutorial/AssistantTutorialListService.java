
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.entities.tutorials.Tutorial;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialListService extends AbstractService<Assistant, Tutorial> {

	//Internal	state ------------------------------------------------------------------------
	@Autowired
	protected AssistantTutorialRepository repository;


	//AbstractService Interface -------------------------------------------------------------
	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Collection<Tutorial> object;
		final int id;

		id = super.getRequest().getPrincipal().getId();
		object = this.repository.findTutorialsByAssistantId(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Offer object) {
		Tuple tuple;

		tuple = super.unbind(object, "instantiation", "heading", "summary", "startDate", "finishDate", "price", "link");

		super.getResponse().setData(tuple);
	}
}
