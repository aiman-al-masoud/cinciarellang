# Immutability

In Cinciarellang, you can obtain an immutable copy of any object with the `freeze()` method:

```
x = {
     'hello' : 'ciao', 
     'good morning' : 'buongiorno'
     }

y = x.freeze();
```

The variable `x` points to the original (mutable) instance of the dictionary, while `y` points to a new *frozen* (immutable) copy of the original.

Trying to do this:

```
y['hello'] = 'salve';
// y.hello = 'salve' // equivalent alternative
```

Will raise an error, because the object pointed to by `y` is immutable.

> Note that: this works recursively. Any sub-object will also be immutable in the frozen copy.

## Primitive Objects

... are immutable by default, that includes: ints, floats, bools and strings.

## Copy

If you wish to obtain a **mutable** deep copy of an object, you can just use the `copy()` method instead:

```
y = x.copy();
y['hello'] = 'salve';  // ok! and x isn't modified!
```


