# Expressions vs Statements

Most things in Cinciarellang are "expressions". The things that aren't, are called "statements". 

What's an expression? Well, it's something that *"returns a value"*, or *"evaluates to a result"* or *"yields a value"* (all synonymous terms).

These are all examples of expressions in Cinciarellang:

```
0
```

```
1+1*3/1  // 4
```

```
'hello world!'
```

```
0 == 0   // true
```

But even:

```
if true then 'yay!' else 'nay!'  
```

... which evaluates to 'yay!'.

```
f = \x -> 2*x
```

... which evaluates to a function.

```
C = class{
    name = 'Bob';
    getName = \-> name;    
}
```

... which evaluates to a class!

Expressions can also be combined together, like building blocks, to yield a more complex expression:

```
if 0 == 0 then 'yay!' else 'nay!'
```

Expressions can also be assigned to variables:

```
x = if 0 == 0 then 'yay!' else 'nay!';
x == 'yay!'; //true
```

## In summary:

* Expressions are things that:
    1. Return a value.
    1. Can be combined together.
    1. Can be assinged to a variable.

* Expressions stand in contrast to statements.

* Most things in Cinciarellang are expressions, and the few statements are the exception(s) (pun intended).

<div style=' margin: auto; width: 50%;'>

[<- Index](../../README.md) |
[Statements ->](./statements.md)

</div>