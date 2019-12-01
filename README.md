# Nullable

**In shortcut:** Better Optional class for Java :) 

**More details:** `Optional` class is great. In many situations can replace if statements.
I encountered a problem with the optional class. I had chain of map methods and couldn't create log between this methods.
I think **Nullable** resolve my problem and I share with you.


## Installation
Add to pom.xml dependency

```xml
<dependency>
  <groupId>me.michalik</groupId>
  <artifactId>nullable</artifactId>
  <version>1.0</version>
</dependency>
```

## Which problem resolve
Here is example of code which represents my problem and solution.

### My problem
If method `ifPresent()` return `false`, which `flatMap` method failed? Solution: Add log between `flatMap` methods, but 
how add log between flatMap methods?

```java
Optional
    .ofNullable(getValue(false))
    .flatMap(value -> firstMap(value))
    .flatMap(value -> secondMap(value))
    .flatMap(value -> thirdMap(value));
```

### Resolve
I didn't find a solution to this problem with the `Optional` class.
I created the "Nullable" class that lets you add a log between methods. 

```java
Nullable
    .of(getValue(false))
    .ifNotPresent(() -> System.out.println("First check is null"))
    .flatMap(value -> firstMap(value, true))
    .ifNotPresent(() -> System.out.println("Second value is null"))
    .flatMap(value -> secondMap(value, false))
    .ifNotPresent(() -> System.out.println("Third value is null"))
    .map(Integer::valueOf)
    .ifPresent(integer -> System.out.println("Result: " + integer));
```

## Have you any idea for next methods and improvements? 
If you had idea for next methods and improvements describe details in issue.
