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