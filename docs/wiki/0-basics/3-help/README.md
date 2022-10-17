# Help

## Type

You can get the type of any object by accessing its "type" property (by the way, everything is an object):

```
x = 1;
x.type; // int
```

```
name = 'Bob';
name.type; // string
```

```
vals = [1,2,3];
vals.type; // int[]
```

```
f = \x:int:int -> 2*x
f.type; // \( x:int) -> int
```


## Entries

You can get a list of entries (name, value) that are present in any object (except for functions, at the moment):

```
C = class { 
    A = 1; 
    B = 2;   
}

C.entries();
```

This outputs:

```
[['help', NativeCode], ['A', 1], ['as', NativeCode], ['entries', NativeCode], ['B', 2], ['freeze', NativeCode], ['this', class{}], ['is', NativeCode], ['copy', NativeCode]]
```

As you can see, there's a bit of builtin methods besides the variables A and B declared in that class.


## Comments