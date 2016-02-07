JParFor (Java Parallel For)
===========================
Multithreading implementation parallel "for" operator in Java.


Requirements
------------
 * Java 1.8 or newer.


Documentation
-------------
[JavaDocs](https://dzavodnikov.github.io/JParFor/)
[Examples](https://github.com/dzavodnikov/JParFor/tree/examples/)


Install
-------
###Maven:

    ...
    <repositories>
        ...
        <repository>
            <id>JParFor</id>
            <url>https://raw.github.com/dzavodnikov/JParFor/mvn-repo/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
        ...
    </repositories>
    ...
    <dependency>
        <groupId>org.jparfor</groupId>
        <artifactId>jparfor</artifactId>
        <version>1.1.29</version>
    </dependency>
    ...

###Gradle:

    ...
    repositories {
        ...
        mavenCentral()
        ...
    }
    ...
    dependencies {
        ...
        compile 'org.jparfor:jparfor:1.1.29'
        ...
    }
    ...

