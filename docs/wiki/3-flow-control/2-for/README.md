# For

The `for` expression is pretty neat, you can actually use it as a **list comprehension**:

```
doubles = for i in [1,2,3,4] -> 2*i;
doubles == [2,4,6,8]; // true
```

You can add a **filtering clause** with the `if` keyword:

```
doubles2 = for i in [1,2,3,4] if i > 2 -> 2*i;
doubles2 == [6,8]; // true
```

... which gets applied before the mapping part happens.


The comprehension above is equivalent to:

```
doubles2 = [1,2,3,4].filter(\i > 2).map(\2 * i);
```

You can also zip together two or more lists using a comprehension:

```
students = ['Alice', 'Bob', 'Charlie'];
grades = [90, 88, 70];

y = for s in students,
        g in grades -> [s, g]; 

y == [["Alice", 90], ["Bob", 88], ["Charlie", 70]]; // true
```



