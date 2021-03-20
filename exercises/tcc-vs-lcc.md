# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

If there is only direct connections, TCC and LCC metrics will produce the same value.

```Java
    class A {
        B b = new B()
        public getA() { b.getB()}
    }

    class B {
        A a = new A()
        public getB() { a.getA()}
    }
```

TCC = NDC/NP
LCC = (NDC+NIC)/NP
So : TCC <= LCC
