# Dictionaries

A dictionary is a mutable mapping between a set of keys and a set of values:

```
 x = {'cat' : 'meow', 'dog' : 'woof', 'monster':'Me luvs goto'}
```

You can set or reset any key to a new value:

```
x['cat'] = 'hiss!';
x['cat'] == 'hiss!'; // true
```

This syntax is completely equivalent, but cannot be used if the name of a key contains a space:

```
x.cat = 'hiss!';
x.cat == 'hiss!'; // true
```











