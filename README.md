# Learning Java

[![Build Status](https://travis-ci.org/feedm3/learning-java.svg)](https://travis-ci.org/feedm3/learning-java)

This repository is used to explore, learn and document different Java libraries and Java 8 features.

[Spock](https://github.com/spockframework/spock) is used as testing framework for the
 Java libraries because of the great readability, syntax and build in features. For the
 Java 8 tests we use JUnit to not mix up some features and methods with groovy.

In his book "[Clean Code](http://www.amazon.de/dp/0132350882)" (Chapter 8: Boundaries), Robert C. Martin recommends 
to write learning tests. Instead of trying out third party code in our production code we write tests to explore 
the API and check if it behaves like we expect.

## Run

To run all test just hit the following command

```
gradlew test
```

After the test you can open the `build/spock-reports/index.html` file in your browser to see all generated test results.

There's also a run configuration for IntelliJ to run all tests.

## Tested libraries

* [guava](src/test/groovy/guava)
    - Google Core Libraries for Java
    - [Project on Github](https://github.com/google/guava)
* [jBcrypt](src/test/groovy/bcrypt)
    - Implementation the OpenBSD Blowfish password hashing algorithm
    - [Project on Github](https://github.com/svenkubiak/jBCrypt)


## Tested Java 8 features

* [Lambdas](src/test/java/LambdasTest)
* [Streams](src/test/java/StreamsTest)

