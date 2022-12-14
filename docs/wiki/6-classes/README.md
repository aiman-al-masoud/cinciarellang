# Classes

Cinciarellang includes a very simple class system, that you can use to do Object Oriented Programming. 

As is the case with functions, all classes are anonymous objects. To create an empty class, you can do this:

```
C = class{};
```

This class is basically an empty blueprint. You can nonetheless use this blueprint to instantiate new objects by calling the variable to which you've assigned the class expression:

```
x = C();
```

This example is a little more elaborate:

```
Person = class{

        name:string;
        age:int;

        __init__ = \name:string, age:int ->{
            this.name = name;
            this.age = age;
        }

        isAdult = \-> age >= 18;

        birthday = \->{
            this.age++;
        }

}
```

You can then try it out by instantiating an object: 
```
p = Person('Bob', 17);
p.isAdult(); // false
p.birthday();
p.isAdult(); // true
```

## (Multiple) Inheritance

There are no ~~extends~~ or ~~implements~~ keywords. Class objects are composable expressions, that can be combined together with the `+` operator, to yield a brand new class object, with no relationship to its "parents".

```
Duck = class {

    __init__ = \name->{
        this.name = name;
   }

   quack = \ -> 'quack!';
}

Greeter = class {
	greet = \-> 'Hi, my name is '+name;
}


GreetingDuck = Duck + Greeter;
duck = GreetingDuck('Double Duck');
duck.quack(); // 'quack!'
duck.greet(); // 'Hi, my name is Double Duck'
```


```
duck.type == Greeter; // false 
Greeter == duck.type; // true
```

If two classes define the same method, the method resulting in their combination is taken from the last class in the addition chain.

## Interfaces

There is no  ~~interface~~ keyword. To achieve the same functionality, you simply create a class that only contains declarations:

```
CoordInterface = class {
    x:int; //must contain an integer x
    y:int; //must contain an integer y
}
```

This implementation works, you can create objects out of it:

```
Coord = CoordInterface + class {

    __init__ = \x,y ->{
        this.x = x;
        this.y = y;
    }
}

c = Coord(1,2);
c.x == 1 && c.y == 2; // true
```


This other implementation can't be instantiated, it's incomplete:

```
WrongCoord = CoordInterface + class{

}

w = WrongCoord(); // TypeError Cannot instantiate class with undefined attributes!
```
















