
package acme.features.assistant.tutorial;

import org.springframework.stereotype.Service;

import acme.entities.tutorials.Tutorial;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialDeleteService extends AbstractService<Assistant, Tutorial> {

	//	@Autowired
	//	protected AssistantTutorialRepository repository;
	//
	//
	//	@Override
	//	public void check() {
	//		boolean status;
	//
	//		status = super.getRequest().hasData("id", int.class);
	//
	//		super.getResponse().setChecked(status);
	//	}
	//
	//	@Override
	//	public void authorise() {
	//		boolean status;
	//		int masterId;
	//		Tutorial tutorial;
	//		Assistant assistant;
	//
	//		masterId = super.getRequest().getData("id", int.class);
	//		tutorial = this.repository.findTutorialById(masterId);
	//		assistant = tutorial == null ? null : tutorial.getAssistant();
	//		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(assistant);
	//
	//		super.getResponse().setAuthorised(status);
	//	}
	//
	//	@Override
	//	public void load() {
	//		Lecture object;
	//		int id;
	//
	//		id = super.getRequest().getData("id", int.class);
	//		object = this.repository.findOneLectureById(id);
	//
	//		super.getBuffer().setData(object);
	//	}
	//
	//	@Override
	//	public void bind(final Lecture object) {
	//		assert object != null;
	//
	//		super.bind(object, "title", "summary", "estimatedLearningTime", "body", "lectureType", "link");
	//	}
	//
	//	@Override
	//	public void validate(final Lecture object) {
	//		assert object != null;
	//	}
	//
	//	@Override
	//	public void perform(final Lecture object) {
	//		assert object != null;
	//
	//		Collection<LectureCourse> lectureCourses;
	//
	//		lectureCourses = this.repository.findManyLectureCoursesByLectureId(object.getId());
	//
	//		this.repository.deleteAll(lectureCourses);
	//		this.repository.delete(object);
	//	}
	//
	//	@Override
	//	public void unbind(final Lecture object) {
	//		assert object != null;
	//		Tuple tuple;
	//		SelectChoices choices;
	//
	//		choices = SelectChoices.from(NatureType.class, object.getLectureType());
	//
	//		tuple = super.unbind(object, "title", "summary", "estimatedLearningTime", "body", "lectureType", "link", "draftMode");
	//		tuple.put("lectureTypes", choices);
	//
	//		super.getResponse().setData(tuple);
	//	}
}
