# Caffeinated
Java 8 source preprocessor, adds new "caf expressions" to Java to help reduce boilerplate.<br>
**Caffeinated** is a full version of the **Caffeine** prototype, which can be found [here](https://github.com/MaowImpl/Caffeine).

## Caffeinated Syntax

### Caf expressions

Caf expressions are small lines of code that sit at the top of your class declaration, if the preprocessor recognizes any in an input file, it will generate a new file based on those expressions.<br>
They start with `#`, and come before the class modifiers, standard convention is that they remain in all lowercase.

#### `data`

`data` is the most simple of all expressions, it generates new setters, getters, and constructor parameters based on the fields in a class that has it, it also retains the value that it has been assigned in the field.<br>
The rules for `data` are simple:
* **Always**: Generate a **getter**.
* If a field is **mutable**: Generate a **setter**.
* If a field is **immutable**: Generate a **constructor parameter** IF there is no value assigned to the field.

**Example**

*Input*

```java
#data
public class Test {
    private final String immutable;
    private final String immutableWithValue = "value";
    private String mutable;
}
```

*Output*

```java
public class Test {
    private final String immutable;

    private final String immutableWithValue = "value";

    private String mutable;

    public Test(String immutable) {
        this.immutable = this.immutable;
    }

    public String getImmutable() {
        return immutable;
    }

    public String getImmutableWithValue() {
        return immutableWithValue;
    }

    public String getMutable() {
        return mutable;
    }

    public void setMutable(String mutable) {
        this.mutable = mutable;
    }
}
```
