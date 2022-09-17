# Pure Functions

All Cinciarellang functions are *pure*, unless explicitly made impure.

A *pure function* doesn't produce any side effects, and its output (return value) only depends on its input (parameters), this means that it **cannot** under any circumstance:

* Read from (or write to) the outer scope.
* Modify its input paramters.

These are all examples of pure functions:

A very simple function that takes one argument:
```
f = \ x ->  2*x +1;
f(10); // 21
```
... two arguments:
```
g = \ x, y -> x + y;
g(1,2); // 3
```

A function with a block-body (requires a return statement):
```
h = \x -> {
    t = 3*x + 1;
    return t;
}
h(1); // 4
```

A (trivial) function that takes no arguments is bound to return a constant value:

```
j = \ -> 100;
j(); // 100
```

Using the match expression to implement a "lookup table":

```
k = \x -> match x {
    "two" -> 2;
    "one" -> 1;
    "three" -> 3;
}

k("one"); //1
```

Trying to read from the outer scope will result in 

```

```



