
package acme.features.lecturer.lectureCourse;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureCourse;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCourseDeleteService extends AbstractService<Lecturer, LectureCourse> {

	@Autowired
	protected LecturerLectureCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("lectureId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int lectureId;
		Lecture lecture;

		lectureId = super.getRequest().getData("lectureId", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		status = lecture != null && super.getRequest().getPrincipal().hasRole(lecture.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		LectureCourse object;
		int lectureId;
		Lecture lecture;

		lectureId = super.getRequest().getData("lectureId", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		object = new LectureCourse();
		object.setLecture(lecture);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final LectureCourse object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		object.setCourse(course);
	}

	@Override
	public void validate(final LectureCourse object) {
		assert object != null;

		super.state(object.getCourse() != null, "course", "lecturer.lecture-course.form.error.no-course-selected");
		if (object.getCourse() != null)
			super.state(object.getCourse().isDraftMode(), "course", "lecturer.lecture-course.form.error.published");
	}

	@Override
	public void perform(final LectureCourse object) {
		assert object != null;

		LectureCourse lectureCourse;

		lectureCourse = this.repository.findOneLectureCourseBy(object.getCourse().getId(), object.getLecture().getId());

		this.repository.delete(lectureCourse);
	}

	@Override
	public void unbind(final LectureCourse object) {
		assert object != null;

		int lectureId;
		Collection<Course> courses;
		final Tuple tuple;
		SelectChoices choices;

		lectureId = super.getRequest().getData("lectureId", int.class);
		courses = this.repository.findCoursesByLectureId(lectureId);

		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = new Tuple();
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<LectureCourse> objects) {
		assert objects != null;
		int lectureId;

		lectureId = super.getRequest().getData("lectureId", int.class);
		super.getResponse().setGlobal("lectureId", lectureId);
	}
}
