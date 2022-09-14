# Broadcasting

Applying certain operations to a list, causes them to be applied elementwise, for example, this is how you can multiply each element of a list by `5`:

```
l = 1 to 5;
x = 5*l;
x; // [5, 10, 15, 20, 25]
```

This is equivalent to either:

```
l = 1 to 5;
x = l.map(\x->5*x);
```

or:

```
l = 1 to 5;
x = [5*i for i in l];
```
