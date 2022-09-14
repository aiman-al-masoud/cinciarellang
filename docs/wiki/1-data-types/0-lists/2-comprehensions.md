# Comprehensions

Comprehensions are a compact notation to express mapping operations performed on a list:

```
l = 1 to 5;
x = [2*x for x in l];
x  //[2, 4, 6, 8, 10];
```

You can add a filtering clause with the `if` keyword:

```
l = 1 to 5;
x = [2*x for x in l if x > 2];
x  // [6, 8, 10];
```

Note that the filter is applied before the map, the comprehension above is perfectly equivalent to applying these two lambdas with list's `filter()` and `map()` methods:

```
l = 1 to 5;
x = l.filter(\x->x>2).map(\x->2*x);
x  // [6, 8, 10];
```