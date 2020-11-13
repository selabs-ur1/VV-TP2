# Cyclomatic Complexity with JavaParser

With the help of JavaParser implement a program that computes the Cyclomatic Complexity (CC) of all methods in a given Java project. The program should take as input the path to the source code of the project. It should produce a report in the format of your choice (TXT, CSV, Markdown, HTML, etc.) containing a table showing for each method: the package and name of the declaring class, the name of the method, the types of the parameters and the value of CC.
Your application should also produce a histogram showing the distribution of CC values in the project. Compare the histogram of two or more projects.


Include in this repository the code of your application. Remove all unnecessary files like compiled binaries. Do include the reports and plots you obtained from different projects. See the [instructions](../subject.md) for suggestions on the projects to use.

You may use [javaparser-starter](../code/javaparser-starter) as a starting point.

*This exercise grants bonus points.*


# Proposition to use the Doodle Project
Some of the function in the doodle have big CC (e.g. ICSResources::parseCalandarforAppointment, UserResource::getAllUserFromPoll) 

These two function are good example to discuss, parseCalandarforAppointment has an inherent complexity while getAllUserFromPoll can be simplified.
In fact, the choice of returning the Users as a list rather than a set and unnecessary checks increase the CC of this function when it is not mandatory.

```java
public ResponseEntity<List<User>> getAllUserFromPoll(@PathVariable String slug) {
    List<User> users = new ArrayList<>();
    // On vérifie que le poll existe
    Poll poll = pollRepository.findBySlug(slug);
    if (poll== null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // On parcours les choix du poll pour récupérer les users ayant voté
    if (!poll.getPollChoices().isEmpty()) {
        for (Choice choice : poll.getPollChoices()) {
            if (!choice.getUsers().isEmpty()) {
                for (User user : choice.getUsers()) {
                    // On vérifie que le user ne soit pas déjà dans la liste
                    if (!users.contains(user)) {
                        users.add(user);
                    }
                }
            }
        }
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
}

// vvvvvvvvvvv   Can be coded as below vvvvvvvvvvvvvvvvv 

public ResponseEntity<List<User>> getAllUserFromPoll(@PathVariable String slug) {
    Set<User> users = new HashSet<>();
    // On vérifie que le poll existe
    Poll poll = pollRepository.findBySlug(slug);
    if (poll== null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // On parcours les choix du poll pour récupérer les users ayant voté
    for (Choice choice : poll.getPollChoices()) {
        users.addAll(choice.getUsers());
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
}
```

For the comparison with other projects histograms, we can use Apache Commons as you propose or compare to a [similar buisness logic application](https://github.com/callicoder/spring-security-react-ant-design-polls-app)