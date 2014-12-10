<%@ page import="tribal.Project" %>



<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'code', 'error')} ">
    <label for="code">
        <g:message code="project.code.label" default="Code"/>

    </label>
    <g:textField name="code" value="${projectInstance?.code}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="project.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${projectInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'techLead', 'error')} required">
    <label for="techLead">
        <g:message code="project.techLead.label" default="Tech Lead"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="techLead" name="techLead.id" from="${tribal.Person.list()}" optionKey="id" required=""
              value="${projectInstance?.techLead?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'projectManager', 'error')} required">
    <label for="projectManager">
        <g:message code="project.projectManager.label" default="Project Manager"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="projectManager" name="projectManager.id" from="${tribal.Person.list()}" optionKey="id" required=""
              value="${projectInstance?.projectManager?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'deliveryDate', 'error')} required">
    <label for="deliveryDate">
        <g:message code="project.deliveryDate.label" default="Delivery Date"/>
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="deliveryDate" precision="day" value="${projectInstance?.deliveryDate}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'currentPhase', 'error')} ">
    <label for="currentPhase">
        <g:message code="project.currentPhase.label" default="Current Phase"/>

    </label>
    <g:select name="currentPhase" from="${projectInstance.constraints.currentPhase.inList}"
              value="${projectInstance?.currentPhase}" valueMessagePrefix="project.currentPhase"
              noSelection="['': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'priority', 'error')} required">
    <label for="priority">
        <g:message code="project.priority.label" default="Priority"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select name="priority" from="${actionName == 'create' ? 1..(Project.count()+1) : 1..(Project.count())}" class="range" required=""
              value="${fieldValue(bean: projectInstance, field: 'priority')}"/>

</div>

