# Exceptions

## Catching them


```

immutableDict = {'hello':'ciao', 'duck':'papero'};

try{
	immutableDict.cat = 'gatto';
} catch e : CannotMutateException{ 
    /* This block will be executed*/
}

```

## Throwing them

You can throw any object, and it will be automatically wrapped in an CinciaException wrapper:

```
throw 'on no!';
```