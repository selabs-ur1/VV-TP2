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

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer
```xml
<rule name="3IfOrMore"
      language="java"
      message="3 or more imbricated ifs detected"
      class="net.sourceforge.pmd.lang.rule.XPathRule">
   <description>

   </description>
   <priority>4</priority>
   <properties>
      <property name="version" value="2.0"/>
      <property name="xpath">
         <value>
<![CDATA[
.//IfStatement[count(ancestor::IfStatement)>=2 and count(descendant::IfStatement)=0]
]]>
         </value>
      </property>
   </properties>
</rule>
```

For exemple, commons-math contains 243 imbrications of 3 or more if statement : [3IfMore_in_commons-math.txt](https://github.com/Guitayk/VV-TP2/files/6150131/3IfMore_in_commons-math.txt)
