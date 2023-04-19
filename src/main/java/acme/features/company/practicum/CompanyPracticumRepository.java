
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.course.id = :id")
	Collection<Practicum> findPracticumByCourse(int id);

	@Query("select p from Practicum p where p.draftMode = false")
	Collection<Practicum> findNotInDraftPracticum();

	@Query("select p from Practicum p where p.company.id = :id")
	Collection<Practicum> findPracticumByCompanyId(int id);

	@Query("select p from Practicum p")
	Collection<Practicum> findAllPracticum();

	@Query("select p from Practicum p where p.code = :code")
	Practicum findAPracticumByCode(String code);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findNotInDraftCourses();

	@Query("select a from Company a where a.id = :id")
	Company findCompanyById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select p from Practicum p where p.code = :code")
	Collection<Practicum> findAllPracticumByCode(String code);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findSessionsByPracticumId(int id);
}
