# Match

You can use a `match` in place of an `if` when you start having a set (more than two) possible conditions to check for, and where each condition may be a little more involved than a simple equality check:


```
y = match x { 
	x < 10 -> 'small'; 
	x > 10 -> 'big'; 
	x == 10 -> 'just right';
}
```

Or equivalently:

```
y = match x { 
	x < 10 -> 'small'; 
	x > 10 -> 'big'; 
	10 -> 'just right';
}
```

You can reference more than one variable in the conditions, provided that the variables are in scope:

```
x = 1;
y = 2;

z = match x { 
	x < 10  &&  y < 10 -> "both less than 10!"; 
	x > 10 			   -> "x greater than 10!"; 
	y > 10 && x < 10   -> "y greater and x less than 10!";
}
```

> Even in this case, **only one** variable has to appear right after the match keyword.


Since `match` is... you guessed it: an expression, you can make it reusable by assigning it directly to a function, and that will create a very practical lookup table:

```
f = \ x-> match x { 
	x < 10 -> 'small'; 
	x > 10 -> 'big'; 
	10 -> 'just right';
}

f(9); // 'small'
f(11); // 'big'
f(10); // 'just right'
```


