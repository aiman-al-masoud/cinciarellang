# While

The `while` loop is the ugliest of them all, too bad it's also the most versatile*. In Cinciarellang, it's an expression:

```
x = 0;
y = while x < 10{
        x++;
    } -> x;

x == [1,2,3,4,5,6,7,8,9,10]
```

But since it doesn't appear to be very useful as an expression, you can always treat it like a statement, ignoring its return value:

```
x = 0;
while x < 10{
        // do sometning
        x++;
} -> x;
```

