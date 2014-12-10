package tribal

class Project {

    String code
    String name
    Person techLead
    Person projectManager
    Date deliveryDate
    String currentPhase
    Integer priority

    static constraints = {
        code(nullable: true, blank: false)
        name()
        techLead()
        projectManager()
        deliveryDate()
        currentPhase(inList: ['Briefing','Scoping','Interaction','Development','QA','Release'])
        priority(validator: { val, obj ->
            Project.withNewSession {
                if (val && !(val in (1..Project.count()+1))) {
                    return "project.priority.validator.invalid"
                }
            }
        })
    }

    static mapping = {
        sort "priority"
    }

    def afterInsert() {
        Project.withNewSession {
            def projectWithSamePriority = Project.findByPriorityAndIdNotEqual(this.priority, this.id)
            if (projectWithSamePriority) {
                projectWithSamePriority.priority += 1
                projectWithSamePriority.save(flush: true)
                projectWithSamePriority.afterInsert()
            }
        }
    }

    def afterUpdate() {
        Project.withNewSession {
            def projectWithSamePriority = Project.findByPriorityAndIdNotEqual(this.priority, this.id)
            if (projectWithSamePriority) {
                projectWithSamePriority.priority += 1
                projectWithSamePriority.save(flush: true)
                projectWithSamePriority.afterUpdate()
            }
        }
    }

    def afterDelete() {
        Project.withNewSession {
            def projectWithSamePriority = Project.findByPriorityAndIdNotEqual(this.priority+1, this.id)
            if (projectWithSamePriority) {
                projectWithSamePriority.priority -= 1
                projectWithSamePriority.save(flush: true)
                projectWithSamePriority.afterDelete()
            }
        }
    }

    String toString() {
        return name
    }
}
