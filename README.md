JParFor (Java Parallel For)
===========================
Multithreading implementation parallel "for" operator in Java.


Requirements
------------
 * Java 1.8 or newer.


Documentation
-------------
[JavaDocs](https://dzavodnikov.github.io/JParFor/)


Examples
--------
[Examples](https://github.com/dzavodnikov/JParFor/tree/examples/)


Install
-------

### Ivy

Add this code to **ivysettings.xml**:

    ...
    <ivysettings>
        <settings defaultResolver="chain" />
        <resolvers>
            <chain name="chain">
                <ibiblio    name="central" m2compatible="true" />
                <ibiblio    name="JParFor" m2compatible="true" 
                            root="https://raw.github.com/dzavodnikov/JParFor/mvn-repo/" />
            </chain>
        </resolvers>
    </ivysettings>
    ...

Add this code to **ivy.xml**:

    ...
    <dependencies>
        <dependency org="org.jparfor" name="jparfor" rev="1.1.29" />
    </dependencies>
    ...

Add this code to *build.xml*:

    ...
    <target name="resolve" description="Retrieve dependencies with Ivy">
        <ivy:retrieve />
    </target>
    ...


### Maven

Add this code to **pom.xml**:

    ...
    <repositories>
        ...
        <repository>
            <id>jparfor</id>
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


### Gradle

Add this code to **build.gradle**:

    ...
    repositories {
        ...
        mavenCentral()
        JParFor {
            url 'https://raw.github.com/dzavodnikov/JParFor/mvn-repo/'
        }
        ...
    }
    ...
    dependencies {
        ...
        compile 'org.jparfor:jparfor:1.1.29'
        ...
    }
    ...

