# If 

As you already know, like most things in Cinciarellang, the `if` construct is an expression. An `if` is great if you have to pick a value based on a binary choice:

```
foo = 2;
x = if foo < 3 then 'small' else 'big';
x == 'small'; //true
```

Because `if` is an expression, you can concatenate any number of `if-else-if`s as you want (although you'll probably prefer using 
[`match`](../1-match/README.md) if you reach that point):

```
foo = 2;

x = if foo < 3 
        then 'small' 
        else if foo > 3 
                then 'big'
                else 'equal';

x == 'small'; //true
```

You can also use `if` with code blocks, in that case you don't need to use the `then` keyword, but you need to `return` the result:

```
foo = 2;

x = if foo < 3 {
    return 'small';
}else{
    return 'big'
}

```

And of course, you can even treat `if` as a statement (by ignoring its return value):

```
foo = 2;

if foo < 3 {
    print('small');
}else{
    print('big')
}

```

> Note that the parentheses `()` around the condition aren't compulsory. This style is consistent across the language, and it helps you avoid typing out unnecessary keys









