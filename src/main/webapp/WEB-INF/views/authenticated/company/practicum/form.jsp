<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="authenticated.company.practicum.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.company.practicum.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.company.practicum.form.label.summary" path="summary"/>
	<acme:input-textarea code="authenticated.company.practicum.form.label.goals" path="goals"/>
	<acme:input-select code="authenticated.company.practicum.form.label.course" path="course" choices="${courses}"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish' ) && draftMode== true}">
			<acme:submit code="authenticated.company.practicum.form.button.update" action="/authenticated//company/practicum/update"/>
			<acme:submit code="authenticated.company.practicum.form.button.delete" action="/authenticated//company/practicum/delete"/>
			<acme:submit code="authenticated.company.practicum.form.button.publish" action="/authenticated//company/practicum/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.company.practicum.form.button.create" action="/authenticated//company/practicum/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>