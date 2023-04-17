
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.tutorials.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialUpdateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		// Cannot update if tutorial doesn't belong to you or tutorial has been published
		super.getResponse().setAuthorised(true);
		final int tutorialId = super.getRequest().getData("id", int.class);
		final Tutorial tutorial = this.repository.findTutorialById(tutorialId);

		final int assistantIdFromTutorial = tutorial.getAssistant().getId();

		final int assistantIdFromLoggedUser = super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(assistantIdFromTutorial == assistantIdFromLoggedUser);
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
	public void bind(final Tutorial object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code");
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;

			existing = this.repository.findTutorialByCode(object.getCode());

			// Code must be new or the same than the 'old' tutorial
			super.state(existing == null || existing.getCode().equals(object.getCode()), "code", "assistant.tutorial.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("course")) {
			final Course existing = object.getCourse();
			super.state(existing != null, "course", "assistant.tutorial.form.error.null-course");
		}
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllPublishedCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "summary", "goals");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
