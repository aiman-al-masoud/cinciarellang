# If 

As you already know, like most things in Cinciarellang, the if construct is an expression:

```
foo = 2;
x = if foo < 3 then 'small' else 'big';
x == 'small'; //true
```

Because `if` is an expression, you can concatenate any number of `if-else-if` as you want (although you'll probably prefer using `match` if you reach that point):

```
foo = 2;

x = if foo < 3 
        then 'small' 
        else if foo > 3 
                then 'large'
                else 'equal';

x == 'small'; //true
```










