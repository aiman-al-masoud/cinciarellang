# Declarations

Declarations are another kind of statement. A declaration is like a promise that a certain variable of a given type will exist, eventually. In some cases, this promise can be enforced by the interpreter, like when not initializing a class's declared field, or when assigning the wrong type to some variable. But this is mostly a feature intended to make the life of a (future) compiler easier.

## Variables

```
x:int;
```
... this declares a variable called "x" of type `int`.

```
x = 'hello world!'; 
```
... this erroneous assignment will throw an exception.


```
x = 1;
```
... this assignment is accepted.

## Functions

Function declaration has a special syntax of its own, also known as "signature":

```
f:\x:int,y:int:int
```
... this declares a function that takes two ints (x and y) and returns another int.


## Lists

You can declare a list of any given type like so:

```
x:int[];
// x = 1; // wrong!
x = [1,2,3] //ok!
```





