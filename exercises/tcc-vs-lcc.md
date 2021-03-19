# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

*TCC* and *LCC* metrics produce the same value when there is no indirect connections between methods in the class.
Here is a java class with that criteria :
```
public class NoIndirectConnection {
	
	public int attribute;
	
	public int getAttribute() {
		return attribute;
	}
	
	public void setAttribute(int newAttribute) {
		attribute = newAttribute;
	}

	public void toString() {
		return "A class with no indeirect connection.";
	}

}

```