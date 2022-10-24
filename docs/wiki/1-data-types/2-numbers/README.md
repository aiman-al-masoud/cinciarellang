# Numbers

Cinciarellang supports integers (`int`) and floats (`float`), which are both immutable types; `int`s are currently constrained to the maximum size of `int` in Java, and `float`s correspond to Java's `double`s. 


```
x = 33 + 2;
x == 35; //true
x.type == int; //true
```

You can combine and `int` with a `float`, and the result will naturally yield another `float`:

```
y = 2 + 2.3;
y == 4.3; //true
y.type == float; //true
```

