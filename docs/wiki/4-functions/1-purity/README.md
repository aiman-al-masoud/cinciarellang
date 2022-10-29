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
dict = {'c' : 0};

p = \ x -> {
    x['c'] = x['c'] + 1;
    return x;
}

y = p(dict);
y['c'] == 1; //true
dict['c'] == 0; //true
```



ref 
nonlocal