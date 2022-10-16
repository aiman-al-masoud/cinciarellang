# Match

`match` is... you guessed it, an expression. You can use it in place of an `if` when you start having a set (more than two) possible conditions to check for, where each condition may be a little more invoved than a simple equality check:


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

You can reference more than one variable in the conditions, provided that the variables are in the scope:


```
x = 1;
y = 2;

z = \x,y -> match x { 
	x < 10  &&  y < 10 -> "both less than 10!"; 
	x > 10 			   -> "x greater than 10!"; 
	y > 10 && x < 10   -> "y greater and x less than 10!";
}
```























