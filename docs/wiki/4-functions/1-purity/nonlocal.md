# nonlocal

You can use the `nonlocal` keyword to allow a function to read from the outer scope:


```
x = 100;
right = nonlocal \ -> x;
right() == 100; // true  
```

This makes the function `right()` impure!