# hakjdb-java
Java client library for [HakjDB](https://github.com/hollowdll/hakjdb) in-memory key-value data store.

Under development!

# Dev setup

Install dependencies
```sh
mvn clean install
```

# Run tests

Integration tests use Testcontainers to spin up HakjDB server instances.
The HakjDB image is pulled from [Docker Hub](https://hub.docker.com/r/hakj/hakjdb).

You need to have Docker installed and running.

Run tests

```sh
mvn test
```

# Generate Java protobuf sources

This compiles the proto files and generates the Java stubs to the target directory
```sh
mvn clean compile
```

# Code format

Code format follows [Google Java Style](https://github.com/google/google-java-format/tree/master).