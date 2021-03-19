# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on it source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false negative). Explain why you would not solve this issue.

## Answer
We picked the*Apache Commons Math*. An issue we think should be solved is :
<i>/home/inc/Téléchargements/commons-math-master/src/main/java/org/apache/commons/math4/stat/descriptive/rank/PSquarePercentile.java:565:	CloneMethodReturnTypeMustMatchClassName:	The return type of the clone() method must be the class name when implements Cloneable
/home/inc/Téléchargements/commons-math-master/src/main/java/org/apache/commons/math4/stat/descriptive/rank/PSquarePercentile.java:565:	CloneThrowsCloneNotSupportedException:	clone() method should throw CloneNotSupportedException
</i>
This issue happened because the class Markers clone method is not well defined. It should call super.clone() to obtain the cloned object reference.

Original source code :
```
private static class Markers implements PSquareMarkers, Serializable {

	private final Marker[] markerArray;

	@Override
        public Object clone() {
            return new Markers(new Marker[] { new Marker(),
                    (Marker) markerArray[1].clone(),
                    (Marker) markerArray[2].clone(),
                    (Marker) markerArray[3].clone(),
                    (Marker) markerArray[4].clone(),
                    (Marker) markerArray[5].clone() });

        }
}
```

Proposed solution :
```
private static class Markers implements PSquareMarkers, Serializable {

	private final Marker[] markerArray;

	@Override
        public Object clone() {
        	Markers Copy = (Markers)super.clone();
        	Copy.markerArray = new Marker[] { new Marker(),
                    (Marker) markerArray[1].clone(),
                    (Marker) markerArray[2].clone(),
                    (Marker) markerArray[3].clone(),
                    (Marker) markerArray[4].clone(),
                    (Marker) markerArray[5].clone() });
            return Copy;
        }
}
```


As a false negative issue we chose the following :
<i>/home/inc/Téléchargements/commons-math-master/src/main/java/org/apache/commons/math4/ode/nonstiff/MidpointStepInterpolator.java:93:	UselessParentheses:	Useless parentheses.</i>

We chose to not solve this issue because useless parentheses do not harm the code; on the conrary, the made the code more readable, therefore more understandable. 