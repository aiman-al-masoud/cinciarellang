# Recursion

Since all functions in Cinciarellang are anonymous, and since they are also pure, you cannot reliably call their name from within their body.

This is because their name isn't defined in their inner scope, it's defined in the outer scope, which a pure function cannot read. And since "their name" is just one (or more, differently named) references to an object in RAM, it can't be bound to their internal environment once and for all.

This is why the variable `this` was chosen to refer to a function from within itself:

```
fib = \n -> n <= 1 ? 1 : this(n-1) + this(n-2);
fib(10); //89
```

Though it may look weird or too wordy, this has the added benefit of making it very explicit when a function is recursive.