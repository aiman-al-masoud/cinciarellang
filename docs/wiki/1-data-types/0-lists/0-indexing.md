# Indexing 

Indexing a list is accomplished through the usual square-bracket notation:

```
x = [1,2,3,4]
x[0] == 1 // true
```

# Fancy Indexing

If you index a list using another list, you can select multiple elements thereof, placing them in a shallow copy of the original:

```
x = [1,2,3,4]
y = x[[2,1]]
y == [3, 2]
```



