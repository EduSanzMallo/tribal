package tribal

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(Project)
@Mock(Person)
class ProjectSpec extends Specification {

    @Shared Person person

    def setup() {
        person = new Person(firstname: 'Test firstname', surname: 'Test surname').save()
    }

    def cleanup() {
    }

    void "test all nullable constraints"() {
        given:
        def project = new Project()

        when:
        project.validate()

        then:
        project.errors['name']
        project.errors['techLead']
        project.errors['projectManager']
        project.errors['deliveryDate']
        project.errors['currentPhase']
        project.errors['priority']

        when:
        project.name = 'Project 1'
        project.techLead = person
        project.projectManager = person
        project.deliveryDate = new Date()
        project.currentPhase = 'Development'
        project.priority = 1

        then:
        project.validate()
    }

    void "test inList constraint from currentPhase"() {
        given:
        def project = new Project()
        project.name = 'Project 1'
        project.techLead = person
        project.projectManager = person
        project.deliveryDate = new Date()
        project.priority = 1

        when:
        project.currentPhase = 'Develop'

        then:
        !project.validate()

        when:
        project.currentPhase = 'Development'

        then:
        project.validate()
    }

    void "test custom validator from priority"() {
        given:
        new Project(name: 'Project 1', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 1).save(flush: true)
        new Project(name: 'Project 2', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 2).save(flush: true)
        new Project(name: 'Project 3', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development', priority: 3).save(flush: true)
        def project = new Project(name: 'Project 4', techLead: person, projectManager: person, deliveryDate: new Date(),
                currentPhase: 'Development')

        when:
        project.priority = 6

        then:
        !project.validate()
        project.errors['priority']

        when:
        project.priority = 4

        then:
        project.validate()
    }

    @Unroll
    void "test all nullable constraints in a table"() {
        setup:
        def project = new Project()

        when:
        project.code = code
        project.name = name
        project.techLead = techLead
        project.projectManager = projectManager
        project.deliveryDate = deliveryDate
        project.currentPhase = currentPhase
        project.priority = priority
        project.validate()

        then:
        project.hasErrors() == !valid

        where:
        code | name | techLead | projectManager | deliveryDate | currentPhase | priority | valid
        "1" | "Test Project" | person | person | new Date() | "Development" | 1 | true
        null | "Test Project" | person | person | new Date() | "Development" | 1 | true
        null | "Test Project" | person | person | new Date() | "Develop" | 1 | false
        null | "Test Project" | person | person | new Date() | "Development" | -1 | false
        "1" | null | person | person | new Date() | "Development" | 1 | false
        "1" | null | null | person | new Date() | "Development" | 1 | false
        "1" | null | null | null | new Date() | "Development" | 1 | false
        "1" | null | null | null | null | "Development" | 1 | false
        "1" | null | null | null | null | null | 1 | false
        "1" | null | null | null | null | null | 0 | false
        "1" | null | null | null | null | null | null | false
    }
}
