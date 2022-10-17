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








