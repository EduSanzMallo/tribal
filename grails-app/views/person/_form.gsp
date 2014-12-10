<%@ page import="tribal.Person" %>



<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'firstname', 'error')} ">
	<label for="firstname">
		<g:message code="person.firstname.label" default="Firstname" />
		
	</label>
	<g:textField name="firstname" value="${personInstance?.firstname}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'surname', 'error')} ">
	<label for="surname">
		<g:message code="person.surname.label" default="Surname" />
		
	</label>
	<g:textField name="surname" value="${personInstance?.surname}"/>

</div>

