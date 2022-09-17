# Pure Functions

All Cinciarellang functions are *pure*, unless explicitly 'contaminated'.

A *pure function* doesn't produce any side effects, and its output (return value) depends entirely on its input (parameters), this means that it **cannot** under any circumstance:

* Read from (or write to) the outer scope.
* Modify its input paramters.

## These are all examples of pure functions:



### 1) A very simple function that takes one argument:
```
f = \ x ->  2*x +1;
f(10); // 21
```
### 2) ... two arguments:
```
g = \ x, y -> x + y;
g(1,2); // 3
```

### 3) A function with a block-body (requires a return statement):
```
h = \x -> {
    t = 3*x + 1;
    return t;
}
h(1); // 4
```

### 4) A (trivial) function that takes no arguments is bound to return a constant value:

```
j = \ -> 100;
j(); // 100
```

### 5) Using the `match` expression to implement a "lookup table":

```
k = \x -> match x {
    "two" -> 2;
    "one" -> 1;
    "three" -> 3;
}

k("one"); //1
```

### 6) Trying to read anything from the outer scope will result in an error when running the function **(undefined variable)**:

```
x = 100;
wrong =\ -> x;
wrong(); // x is undefined
```

### 7) All parameters are passed by value, **even objects, for real**:

```
dict = {"c" : 0};

p = \ x -> {
    x["c"] = x["c"] + 1;
    return x;
}

y = p(dict);
y["c"] == 1; //true
dict["c"] == 0; //true
```
