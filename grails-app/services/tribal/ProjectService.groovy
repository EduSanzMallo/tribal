package tribal

import grails.validation.ValidationException

class ProjectService {

    static transactional = true
    def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()

    List<Project> listProjects(params) {
        Project.list(params)
    }

    Project saveProject(Project projectInstance) {
        if (!projectInstance.save()) {
            throw new ValidationException(
                    g.message(code: 'default.created.message', args: ['Project', projectInstance.id]), projectInstance.errors)
        }

        return projectInstance
    }

    void deleteProject(Project projectInstance) {
        projectInstance.delete()
    }
}
