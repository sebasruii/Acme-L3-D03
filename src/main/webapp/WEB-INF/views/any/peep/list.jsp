<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.peep.list.label.instantiation" path="instantiation" width="15%"/>
	<acme:list-column code="authenticated.peep.list.label.nick" path="nick" width="15%"/>
	<acme:list-column code="authenticated.peep.list.label.title" path="title" width="75%"/>
</acme:list>

<acme:button code="any.peep.list.button.publish" action="/any/peep/create"/>