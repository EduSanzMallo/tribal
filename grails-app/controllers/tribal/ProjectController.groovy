package tribal

import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

class ProjectController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def projectService

    def index() {
        redirect(action: 'list', params: params)
    }

    def list() {
        params.max = Math.min(grailsApplication.config.tribal.pagination.pageSize.toInteger() ?: 10, 100)
        def results = projectService.listProjects(params)
        render(view: 'list', model: [projectInstanceList: results, projectInstanceCount: results.getTotalCount()])
    }

    def create() {
        render(view: 'create', model: [projectInstance: new Project(params)])
    }

    def save() {
        def projectInstance = new Project(params)
        try {
            if (projectService.saveProject(projectInstance)) {
                flash.message = message(code: 'default.created.message', args: ['Project', projectInstance.id])
                redirect(action: 'show', id: projectInstance.id)
            } else {
                render(view: 'create', model: [projectInstance: projectInstance])
            }
        } catch (ValidationException e) {
            projectInstance.errors = e.errors
            render(view: 'create', model: [projectInstance: projectInstance])
        }
    }

    def edit(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.error = message(code: 'default.not.found.message', args: ['Project', id])
            redirect(action: 'show', id: projectInstance.id)
        }
        render(view: 'edit', model: [projectInstance: projectInstance])
    }

    def update(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.error = message(code: 'default.not.found.message', args: ['Project', id])
            render(view: 'edit', model: [projectInstance: projectInstance])
        }

        projectInstance.properties = params

        try {
            if (projectService.saveProject(projectInstance)) {
                flash.message = message(code: 'default.updated.message', args: ['Project', projectInstance.id])
                redirect(action: 'show', id: projectInstance.id)
            } else {
                render(view: 'edit', model: [projectInstance: projectInstance])
            }
        } catch (ValidationException e) {
            projectInstance.errors = e.errors
            render(view: 'edit', model: [projectInstance: projectInstance])
        }
    }

    def show(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.error = message(code: 'default.not.found.message', args: ['Project', id])
            redirect(action: 'list')
        }
        render(view: 'show', model: [projectInstance: projectInstance])
    }

    def delete(Long id) {
        def projectInstance = Project.get(id)
        if (!projectInstance) {
            flash.error = message(code: 'default.not.found.message', args: ['Project', id])
            redirect(action: 'show', id: projectInstance.id)
        }

        try {
            projectService.deleteProject(projectInstance)
            flash.message = message(code: 'default.deleted.message', args: ['Project', projectInstance.id])
            redirect(action: 'list')
        } catch (DataIntegrityViolationException e) {
            flash.error = message(code: 'default.not.deleted.message', args: ['Project', projectInstance.id])
            redirect(action: 'show', id: projectInstance.id)
        }
    }
}
