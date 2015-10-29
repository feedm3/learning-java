# Learning Java

[![Build Status](https://travis-ci.org/feedm3/learning-java.svg)](https://travis-ci.org/feedm3/learning-java)

This repository is used to explore, learn and document different Java libraries.

[Spock](https://github.com/spockframework/spock) is used as testing framework because of the
great readability, syntax and build in features.

In his book "[Clean Code](http://www.amazon.de/dp/0132350882)" (Chapter 8: Boundaries), Robert C. Martin recommends 
to write learning tests. Instead of trying out third party code in our production code we write tests to explore 
the API and check if it behaves like we expect.

## Run

To run all test just hit the following command

```
gradlew test
```

After the test you can open the `build/spock-reports/index.html` file in your browser to see all generated test results.

## Tested libraries

* [guava](https://github.com/google/guava)
    - Google Core Libraries for Java
    - [Show me the tests](src/test/groovy/guava)
* [jBcrypt](https://github.com/svenkubiak/jBCrypt)
    - Implementation the OpenBSD Blowfish password hashing algorithm
    - [Show me the tests](src/test/groovy/bcrypt)