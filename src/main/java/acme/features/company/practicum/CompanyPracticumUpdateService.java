
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicums.Practicum;
import acme.features.authenticated.company.AuthenticatedCompanyRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected CompanyPracticumRepository		repository;

	@Autowired
	protected AuthenticatedCompanyRepository	repositoryCompany;


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
		int practicumId;
		Practicum practicum;
		Company company;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		company = practicum == null ? null : practicum.getCompany();

		status = practicum != null && !practicum.getDraftMode() == false || super.getRequest().getPrincipal().hasRole(company);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;
		int CompanyId;
		Company Company;
		int courseId;
		Course course;
		CompanyId = super.getRequest().getPrincipal().getActiveRoleId();
		Company = this.repository.findCompanyById(CompanyId);
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		super.bind(object, "code", "title", "summary", "goals");
		object.setCompany(Company);
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
		final Practicum otherPracticum;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			otherPracticum = this.repository.findAPracticumByCode(object.getCode());
			super.state(otherPracticum == null || otherPracticum.getCode().equals(object.getCode()) && otherPracticum.getId() == object.getId(), "code", "Company.Practicum.form.error.code-uniqueness");
		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		SelectChoices choices;
		Collection<Course> courses;
		Tuple tuple;
		courses = this.repository.findNotInDraftCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = super.unbind(object, "code", "title", "summary", "goals");
		tuple.put("draftMode", object.getDraftMode());
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}
}
