![Travis JParFor master status](https://travis-ci.org/dzavodnikov/JParFor.svg?branch=master)


Overview
========
JParFor (Java Parallel For) is multithreading implementation parallel "for" operator in Java.


Requirements
============
 * Java 1.8 or newer.


Documentation
=============
[JavaDocs](https://dzavodnikov.github.io/JParFor/)


Examples
========
[Examples](https://github.com/dzavodnikov/JParFor/tree/examples/)


Use
===
Gradle
------
    ...
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io/" }
		}
	}
	...
    dependencies {
        ...
        compile "com.github.dzavodnikov:JParFor:1.1.29"
        ...
    }
    ...

Maven
-----
    <project>
        ...
	    <repositories>
		    <repository>
		        <id>jitpack.io</id>
		        <url>https://jitpack.io/</url>
		    </repository>
	    </repositories>
        ...
        <dependencies>
            ...
	        <dependency>
	            <groupId>com.github.dzavodnikov</groupId>
	            <artifactId>JParFor</artifactId>
	            <version>1.1.29</version>
	        </dependency>
	        ...
	    <dependencies>
	    ...
	</project>

