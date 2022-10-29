# Pure Functions

All Cinciarellang functions are *pure*, unless explicitly stated otherwise.

A *pure function* doesn't produce any side effects, and its output (return value) depends solely on its input (parameters), this means that it **cannot** under any circumstances:

* Read from (or write to) the outer scope.
* Modify its input parameters.


### Outer Scope

Trying to read anything from the outer scope will result in an error when running the function **(undefined variable)**:

```
x = 100;
wrong =\ -> x;
wrong(); // x is undefined
```

### Parameters

All parameters are passed by value, **even objects, for real**:

```
myDict = {'c' : 0};

wrong = \ x -> {
    x['c'] = x['c'] + 1;
    x['c'] == 1; //true
}

wrong(myDict);
myDict['c'] == 0; //true
```

You can opt out of the default behaviour (purity) by using the keywords [`ref`](./ref.md) and [`nonlocal`](./nonlocal.md).