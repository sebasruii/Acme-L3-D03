
package acme.features.lecturer.course;

import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

}
