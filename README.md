# Learning Java

[![Build Status](https://img.shields.io/travis/feedm3/learning-java.svg?style=flat-square)](https://travis-ci.org/feedm3/learning-java)

This repository is used to explore, learn and document different Java libraries and Java (especially Java 8) features.

[Spock](https://github.com/spockframework/spock) is used as testing framework for the
 Java libraries because of the great readability, syntax and build in features. For the
 Java API tests we use JUnit to not mix up some features and methods from groovy.

In his book "[Clean Code](http://www.amazon.de/dp/0132350882)" (Chapter 8: Boundaries), Robert C. Martin recommends 
to write learning tests. Instead of trying out third party code in our production code we write tests to explore 
the API and check if it behaves like we expect.


## Run
To run all test just hit the following command or use the IntelliJ run configuration from this repo

```
gradlew test
```

After the test you can open the `build/reports/tests/index.html` file in your browser to see all generated test results.
There are also more detailed reports for all spock tests in `build/spock-reports/index.html`.


## Tested libraries
* [guava](src/test/groovy/guava)
    - Google Core Libraries for Java
    - [Project on Github](https://github.com/google/guava)
* [jBcrypt](src/test/groovy/bcrypt)
    - Implementation the OpenBSD Blowfish password hashing algorithm
    - [Project on Github](https://github.com/svenkubiak/jBCrypt)
* [caffeine](src/test/groovy/caffeine)
    - A high performance, near optimal caching library based on Java 8
    - [Project on Github](https://github.com/ben-manes/caffeine)


## Tested Java features
* [Lambdas](src/test/java/java8/LambdasTest.java)
* [Streams](src/test/java/java8/StreamsTest.java)
* [Date and Time](src/test/java/java8/DateTimeTest.java)
* [Clone objects](src/test/java/general/CloneTest.java)

