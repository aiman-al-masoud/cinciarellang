# Comprehensions

Comprehensions are a compact notation to express mapping operations performed on a list:

```
l = 1 to 5;
y = for x in l -> 2*x;
y;  //[2, 4, 6, 8, 10];
```

You can add a filtering clause with the `if` keyword:

```
l = 1 to 5;
y = for x in l if x > 2 -> 2*x;
y; // [6, 8, 10];
```

Note that the filter is applied before the map, the comprehension above is equivalent to applying these two lambdas with list's `filter()` and `map()` methods:

```
l = 1 to 5;
x = l.filter( \x->x>2 ).map( \x->2*x );
x;  // [6, 8, 10];
```

You can also zip together two or more lists using a comprehension:

```
students = ['Alice', 'Bob', 'Charlie'];
grades = [90, 88, 70];

y = for s in students,
        g in grades -> [s, g]; 

y == [["Alice", 90], ["Bob", 88], ["Charlie", 70]]; // true
```









