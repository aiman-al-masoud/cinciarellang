# Assignment

The single equal sign `=` is the only assignment operator in Cinciarellang.  It binds a variable name on the left, to a value on the right, as you would expect. It is not to be confused with the `==` (double equals) used for logical comparison.

```
x = 1;  // assignment 
x == 1; // true
```
An assignment is an expression (it evaluates to the rightmost rval), like in most other C-like languages, it is evaluated from right to left:

```
(x = y = z = 33) == 3;
```
