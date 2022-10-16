# For

## List Comprehensions

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

... which gets applied before the mapping part happens, ie: the comprehension above is equivalent to:

```
doubles2 = [1,2,3,4].filter(\i > 2).map(\2 * i);
```

## Combining Iterables

You can also zip together, or combine, two or more lists using a comprehension:

```
students = ['Alice', 'Bob', 'Charlie'];
grades = [90, 88, 70];

y = for s in students,
        g in grades -> [s, g]; 

y == [["Alice", 90], ["Bob", 88], ["Charlie", 70]]; // true
```

## With a Block

You can also use the `for` expression with a block:

```
y = for i in 1 to 3{
        z = 2*i + 1;
    } -> z;

y == [2,4,6];
```

## As a Statement

In case you omit the `->`, the `for` loop will return an empty list. You can of course choose to ignore the return value of a `for` loop, and treat it like a statement:

```
for i in 1 to 3{
    print(i);
}
```

## With Unpacking

If an iterable is made up of iterable elements, the `for` loop can automatically unpack each element:

```
studentsGrades = [["Alice", 90], ["Bob", 88], ["Charlie", 70]];
messages = for name, 
               grade in studentsGrades -> name+' got '+grade+' out of 100'; 
```

Evaluates to:

```
['Alice got 90 out of 100', 'Bob got 88 out of 100', 'Charlie got 70 out of 100']
```




