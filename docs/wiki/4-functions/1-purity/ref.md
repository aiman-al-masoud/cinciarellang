# ref

You can use the `ref` keyword to force an argument to be passed by reference:

```
myDict = {'c' : 0};

right = \ ref x -> {
    x['c'] = x['c'] + 1;
}

right(myDict);
myDict['c'] == 1; //true
```

This makes the function `right()` impure!