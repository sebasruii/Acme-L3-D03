<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum.list.label.code" path="code" width="10%"/>
	<acme:list-column code="company.practicum.list.label.title" path="title" width="30%"/>
	<acme:list-column code="company.practicum.list.label.summary" path="summary" width="60%"/>
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="company.practicum.list.button.create" action="/company/practicum/create"/>
</jstl:if>		
