package tribal

import org.springframework.dao.DataIntegrityViolationException

class PersonController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        redirect(action: 'list', params: params)
    }

    def list() {
        params.max = Math.min(grailsApplication.config.tribal.pagination.pageSize.toInteger() ?: 10, 100)
        render(view: 'list', model: [personInstanceList: Person.list(params), personInstanceCount: Person.count()])
    }

    def create() {
        render(view: 'create', model: [personInstance: new Person(params)])
    }

    def save() {
        def personInstance = new Person(params)
        if (personInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: ['Person', personInstance.id])
            redirect(action: 'show', id: personInstance.id)
        } else {
            render(view: 'create', model: [personInstance: personInstance])
        }
    }

    def edit(Long id) {
        def personInstance = Person.get(id)
        if (!personInstance) {
            flash.error = message(code: 'default.not.found.message', args: ['Person', id])
            redirect(action: 'show', id: personInstance.id)
        }
        render(view: 'edit', model: [personInstance: personInstance])
    }

    def update(Long id) {
        def personInstance = Person.get(id)
        if (!personInstance) {
            flash.error = message(code: 'default.not.found.message', args: ['Person', id])
            render(view: 'edit', model: [personInstance: personInstance])
        }

        personInstance.properties = params

        if (personInstance.save(flush: true)) {
            flash.message = message(code: 'default.updated.message', args: ['Person', personInstance.id])
            redirect(action: 'show', id: personInstance.id)
        } else {
            render(view: 'edit', model: [personInstance: personInstance])
        }
    }

    def show(Long id) {
        def personInstance = Person.get(id)
        if (!personInstance) {
            flash.error = message(code: 'default.not.found.message', args: ['Person', id])
            redirect(action: 'list')
        }
        render(view: 'show', model: [personInstance: personInstance])
    }

    def delete(Long id) {
        def personInstance = Person.get(id)
        if (!personInstance) {
            flash.error = message(code: 'default.not.found.message', args: ['Person', id])
            redirect(action: 'show', id: personInstance.id)
        }

        try {
            personInstance.delete()
            flash.message = message(code: 'default.deleted.message', args: ['Person', personInstance.id])
            redirect(action: 'list')
        } catch (DataIntegrityViolationException e) {
            log.error e
            e.printStackTrace()
            flash.error = message(code: 'default.not.deleted.message', args: ['Person', personInstance.id])
            redirect(action: 'show', id: personInstance.id)
        }
    }
}
