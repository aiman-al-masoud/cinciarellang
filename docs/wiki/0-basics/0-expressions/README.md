# Expressions vs Statements

Most things in Cinciarellang are "expressions". The things that aren't, are called "statements". 

What's an expression? Well, it's something that "returns a value", or "evaluates to a result" or "yields a value" (all synonymous terms).

These are all examples of expressions in Cinciarellang:

```
0
```

```
1+1*3/1
```

```
"hello world!"
```

```
0 == 0 
```

But even:

```
if true then "yay!" else "nay!"
```

```
f = \x -> 2*x
```

```
C = class{}
```

Expressions can also be combined together, like building blocks, to yield a more complex expression. Expressions can also be assigned to variables:

```
x = if 0 == 0 then "yay!" else "nay!";
x == "yay!";  //true
```

## In summary:

### 1) Expressions are things that:

1. Return a value.
1. Can be combined together.
1. Can be assinged to a variable.

### 2) Expressions stand in contrast to statements.

### 3) Most things in Cinciarellang are expressions, and the few statements are the exception(s) (pun intended).