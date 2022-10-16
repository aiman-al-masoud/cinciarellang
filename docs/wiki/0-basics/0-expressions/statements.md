# Statements

An example of a statement in Cinciarellang is this:

```
throw 'on no!';
```

This line of code will throw an exception. The exception will unwind the call stack till it's caught by a `try`-`catch` block, if there is one.

This is a statement, and **not** an expression, because:

1. It doesn't return a value, it's 'void'. 
1. It cannot be combined with anything else.
1. It can't be assigned to a variable (it causes the program to exit from the current block of code, before any assignment operation can happen!)

## What else is a statement in Cinciarellang?

1. Throw Statement (we said it).
1. Try-Catch Block.
1. Declarations.
1. Imports.
1. And maybe something more ...

Really, everything else is an expression.

<div style=' margin: auto; width: 50%;'>

[<- Expressions](./README.md) 

</div>