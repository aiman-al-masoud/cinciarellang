# Spread

Also known as the "unpacking" operator. It can be used in Cinciarellang to build a new list from an already existing one, adding some extra elements, all in one expression:

```
old = [1,2,3];
new = [*old, 4, 5, 6];
new == [1,2,3,4,5,6]; // true
```

> Note that: the unpacking is NOT performed recursively.
