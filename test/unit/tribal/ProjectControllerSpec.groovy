package tribal

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(ProjectController)
@Mock([Project, Person])
class ProjectControllerSpec extends Specification {

    Person person

    def setup() {
        person = new Person(firstname: 'Test firstname', surname: 'Test surname').save()
    }

    def cleanup() {
    }

    void "test index action"() {
        when:
        controller.index()

        then:
        response.redirectedUrl == '/project/list'
    }

    void "test list action"() {
        when:
        defineBeans {
            projectService(ProjectService)
        }
        controller.list()

        then:
        view == '/project/list'
        model.projectInstanceCount == 0
    }

    void "test save action"() {
        when:
        params.name = 'Project 1'
        params['techLead.id'] = person.id
        params['projectManager.id'] = person.id
        params.deliveryDate = new Date()
        params.currentPhase = 'Development'
        params.priority = 1

        defineBeans {
            projectService(ProjectService)
        }
        controller.save()

        then:
        response.redirectedUrl == '/project/show/1'
    }

    void "test update action"() {
        given:
        def project = new Project(name: 'Project 1', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 1).save()

        when:
        params.name = 'Project 1A'
        params.currentPhase = 'Interaction'

        defineBeans {
            projectService(ProjectService)
        }
        controller.update(project.id)

        then:
        response.redirectedUrl == '/project/show/1'
    }

    void "test delete action"() {
        given:
        def project = new Project(name: 'Project 1', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 1).save()

        when:
        defineBeans {
            projectService(ProjectService)
        }
        controller.delete(project.id)

        then:
        response.redirectedUrl == '/project/list'
    }
}
