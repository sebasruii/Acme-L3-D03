<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${_command == 'delete'}">
			<acme:input-select code="lecturer.lectureCourse.form.label.course" path="course" choices="${courses}"/>		
		</jstl:when>	
		<jstl:when test="${_command == 'add'}">
			<acme:input-select code="lecturer.lectureCourse.form.label.lecture" path="lecture" choices="${lectures}"/>		
		</jstl:when>	
	</jstl:choose>
	<jstl:choose>
		<jstl:when test="${_command == 'add'}">
			<acme:submit code="lecturer.lectureCourse.form.button.add" action="/lecturer/lecture-course/add?courseId=${courseId}"/>
		</jstl:when>	
		<jstl:when test="${_command == 'delete'}">
			<acme:submit code="lecturer.lectureCourse.form.button.delete" action="/lecturer/lecture-course/delete?lectureId=${lectureId}"/>
		</jstl:when>	
	</jstl:choose>
</acme:form>
