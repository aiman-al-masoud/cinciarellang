# Syntax

You can create a function like this:

```
f = \x, y -> {
    res = x + y;
    return res;
}
```

this is the "classical" syntax with declared parameters and a code block, containing one or more return statements. You can place as many statements as you like within a block. Statements are separated by a semicolon.


Or like this:

```
f = \x, y -> x + y;
```

this is a terser syntax, that doesn't require a return statement, but only allows for one expression to be returned.

Or even like this:

```
f = \x + y;
```

this is an even terser syntax, that doesn't require you to declare the parameters, it just binds the arguments to them by position.

All of the functions above will behave the same way (in theory):

```
f(1, 2) == 3; // true
```











