# Pipes

This is a purely syntactical feature (syntactical sugar) to make chained function invocations look prettier, consider:

```
y = f(g(h(1)));
```

...it looks horrible! In Cinciarellang you can re-write it as:

```
y = 1 | h | g | f;
```

The first member of the chain (on the left) is an expression, the number `1` in this case, and the rest are functions, which appear in the order of their invocation.


This syntactical feature looks especially neat with parameter-less lambdas as the functions of the chain:

```
y = 2 | \ x + 2 | \ x * 3;
y == 12;
```


You also can use this trick to compose functions, and invoke them later:

```
composed = \x -> x | h | g | f;
```





