import tribal.Person
import tribal.Project

class BootStrap {

    def init = { servletContext ->

        //Add new people
        def people = ['Eduardo':'Sanz','Tom':'Smith','John':'Martin']
        people.each {
            new Person(firstname: it.key, surname: it.value).save(failOnError: true)
        }

    }
    def destroy = {
    }
}
