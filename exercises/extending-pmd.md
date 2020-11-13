# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/pmd-6.28.0/pmd_userdocs_extending_writing_rules_intro.html

Use your rule with different projects and describe you findings below. See the [instructions](../subject.md) for suggestions on the projects to use.

## Answer



# Proposition to use the Doodle Project
Some of the function in the doodle have nested control flow elements (e.g. ICSResources::parseCalandarforAppointment, UserResource::getAllUserFromPoll) 

See [Cyclomatic complexity](jp-cc.md)

The following example is a good candidate of such PMD rules (and can easily be corrected to follow the rule)

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
```

For the comparison with other projects histograms, we can use Apache Commons as you propose or compare to a [similar buisness logic application](https://github.com/callicoder/spring-security-react-ant-design-polls-app)