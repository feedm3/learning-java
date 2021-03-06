# Learning Java

[![Build Status](https://img.shields.io/travis/feedm3/learning-java.svg?style=flat-square)](https://travis-ci.org/feedm3/learning-java)
[![Dependency Status](https://dependencyci.com/github/feedm3/learning-java/badge?style=flat-square)](https://dependencyci.com/github/feedm3/learning-java)

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

## Util

To check for the latest dependency versions run

```
gradlew dependencyUpdates -Drevision=release
```


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
* [Java JWT](src/test/groovy/jwt)
    - Java implementation of JSON Web Token (JWT)
    - [Project on Github](https://github.com/auth0/java-jwt)


## Tested Java features

* [Lambdas](src/test/java/jdk8/LambdasTest.java)
* [Streams](src/test/java/jdk8/StreamsTest.java)
* [Functional Interfaces](src/test/java/jdk8/FunctionalInterfacesTest.java)
* [Date and Time](src/test/java/jdk8/DateTimeTest.java)
* [Localization](src/test/java/jdk8/LocalizationTest.java)
* [Optional](src/test/java/jdk8/OptionalTest.java)
* [ConcurrentHashMap](src/test/java/jdk8/ConcurrentHashMapTest.java)
* [Clone objects](src/test/java/jdk1/CloneTest.java)

