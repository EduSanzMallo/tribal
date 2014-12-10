package tribal

class Person {

    String firstname
    String surname

    static constraints = {
        firstname()
        surname()
    }

    String toString() {
        return firstname
    }
}
