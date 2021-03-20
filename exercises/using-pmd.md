# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on it source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false negative). Explain why you would not solve this issue.

## Answer

We decided to test PMD with the javaparser-starter project. We got this :

```
Main.java:3: UnusedImports:  Avoid unused imports such as 'com.github.javaparser.Problem'
Main.java:4: UnusedImports:  Avoid unused imports such as 'com.github.javaparser.ast.CompilationUnit'
Main.java:5: UnusedImports:  Avoid unused imports such as 'com.github.javaparser.ast.body.ClassOrInterfaceDeclaration'
Main.java:6: UnusedImports:  Avoid unused imports such as 'com.github.javaparser.ast.body.MethodDeclaration'
Main.java:7: UnusedImports:  Avoid unused imports such as 'com.github.javaparser.ast.visitor.VoidVisitor'
Main.java:8: UnusedImports:  Avoid unused imports such as 'com.github.javaparser.ast.visitor.VoidVisitorAdapter'
Main.java:13:       UnusedImports:   Avoid unused imports such as 'java.nio.file.Path'
Main.java:14:       UnusedImports:   Avoid unused imports such as 'java.nio.file.Paths'
Main.java:16:       UseUtilityClass: All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.
PublicElementsPrinter.java:4:        UnusedImports:  Avoid unused imports such as 'com.github.javaparser.ast.body'
PublicElementsPrinter.java:20:       ControlStatementBraces: This statement should have braces
PublicElementsPrinter.java:28:       ControlStatementBraces: This statement should have braces
PublicElementsPrinter.java:44:       ControlStatementBraces: This statement should have braces
```

### False negative : 
- If whithout braces is not a mistake is just that there is only one instruction in the if. (PublicElementsPrinter.java:20: ControlStatementBraces: This statement should have braces)
- The static analyser can't seel all the packages importeed even if they ar useful, it is a bad practice, better import only used classes/packages (PublicElementsPrinter.java:4:        UnusedImports:  Avoid unused imports such as 'com.github.javaparser.ast.body')

### True positive : 
All Main.java warnings are true positive

## Commons Math
Running PMD on Commons Math return 2941 lines of warnings : [analyse_commons_math.txt](https://github.com/Guitayk/VV-TP2/files/6149598/analyse_commons_math.txt)
