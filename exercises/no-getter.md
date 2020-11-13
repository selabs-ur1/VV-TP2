# No getter!

With the help of JavaParser implement a program that obtains the private fields of public classes that have no public getter in a Java project. 

A field has a public getter if, in the same class, there is a public method that simply returns the value of the field and whose name is `get<name-of-the-field>`.

For example, in the following class:

```Java

class Person {
    private int age;
    private String name;
    
    public String getName() { return name; }

    public boolean isAdult() {
        return age > 17;
    }
}
```

`name` has a public getter, while `age` doesn't.

The program should take as input the path to the source code of the project. It should produce a report in the format of your choice (TXT, CSV, Markdown, HTML, etc.) that lists for each detected field: its name, the name of the declaring class and the package of the declaring class.

Include in this repository the code of your application. Remove all unnecessary files like compiled binaries. See the [instructions](../subject.md) for suggestions on the projects to use.

*Disclaimer* In a real project not all fields need to be accessed with a public getter.


# Proposition to use the Doodle Project

It seems that there is all the getter in the project, but remains particularly relevant to have an automatic verification of that for DTO objects (Warning : boolean getter start with 'is' is that normal that you don't mention that)

In addition, I propose to make them compute other code analysis :

- Large methods detection
- Performance related ('.get(i)' on list that are not explicitly ArrayLists)
- Verify visibility of attributes (ex : static package visibility in ExportResource.java)  