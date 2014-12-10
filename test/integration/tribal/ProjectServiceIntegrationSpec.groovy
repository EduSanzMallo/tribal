package tribal

import grails.test.spock.IntegrationSpec
import grails.validation.ValidationException
import spock.lang.Ignore

class ProjectServiceIntegrationSpec extends IntegrationSpec {

    static transactional = true
    Person person
    def projectService

    def setup() {
        person = new Person(firstname: 'Test firstname', surname: 'Test surname').save()
    }

    def cleanup() {
    }

    void "test saveProject method"() {
        when:
        def project = new Project(name: 'Project 1', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 1)
        def instanceSaved = projectService.saveProject(project)

        then:
        instanceSaved instanceof Project
        instanceSaved.id
    }

    void "test saveProject method - fails"() {
        when:
        def project = new Project(name: 'Project 1', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development')
        def instanceSaved = projectService.saveProject(project)

        then:
        thrown ValidationException
    }

    void "test deleteProject method"() {
        when:
        def project = new Project(name: 'Project 1', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 1).save()
        projectService.deleteProject(project)

        then:
        Project.count() == 0
    }

    void "test listProjects method"() {
        when:
        new Project(name: 'Project 1', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 1).save()
        new Project(name: 'Project 2', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 2).save()
        new Project(name: 'Project 3', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 3).save()

        def params = [:]
        params['max'] = 5
        params['offset'] = 0
        params['sort'] = "priority"
        params['order'] = "asc"
        def results = projectService.listProjects(params)

        then:
        results.getTotalCount() == 3
    }

    @Ignore
    void "test gorm events with priority field"() {
        //To test after gorm events we need to flush session in each save but
        //I found this bug for 2.3.7 - https://jira.grails.org/browse/GRAILS-11254
        given:
        def project1 = new Project(name: 'Project 1', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 1).save(flush: true)
        def project2 = new Project(name: 'Project 2', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 2).save(flush: true)
        def project3 = new Project(name: 'Project 3', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 3).save(flush: true)

        when:
        def project4 = new Project(name: 'Project 4', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 1).save(flush: true)

        then:
        project4.priority == 1
        project1.priority == 2
        project2.priority == 3
        project3.priority == 4

        when:
        project4.priority = 4
        project4.save(flush: true)

        then:
        project1.priority == 1
        project2.priority == 2
        project3.priority == 3

        when:
        project2.delete(flush: true)

        then:
        project1.priority == 1
        project3.priority == 2
        project4.priority == 3
    }
}
