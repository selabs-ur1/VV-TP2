# Class cohesion with JavaParser

With the help of JavaParser implement a program that computes the Tight Class Cohesion (TCC) for each class in a given Java project. The program should take as input the path to the source code of the project. It should produce a report in the format of your choice (TXT, CSV, Markdown, HTML, etc.) containing a table showing for each class: the package, name and TCC value. 
Your application should also produce a histogram showing the distribution of CC values in the project. Compare the histogram of two or more projects.

Ignore inherited members to compute TCC of a class.

Include in this repository the code of your application. Remove all unnecessary files like compiled binaries. Do include the reports and plots you obtained from different projects. See the [instructions](../subject.md) for suggestions on the projects to use.

You may use [javaparser-starter](../code/javaparser-starter) as a starting point.

*This exercise grants bonus points.*

**Additional Extra Bonus**: Make your application produce the dependency graph of all classes. The graph should be written using the [GraphViz DOT format](https://www.graphviz.org/)


# Proposition to use the Doodle Project

I must compute myself the TCC to see the results but the doodle has probably a good TCC apart from the Utils class (Which is obvious for Utils) and the Export facilities' retrieveUsers that could be mutualized for use in export and "getAllUserFromPoll" which is basically code duplication.

Anyway the DOT Format Graph remains a good info to got and even if the TCC is good implement the tool is still relevant.