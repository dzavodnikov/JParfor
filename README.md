![Travis JParFor master status](https://travis-ci.org/dzavodnikov/JParFor.svg?branch=master)


Overview
========
JParFor (Java Parallel For) is multithreading implementation parallel "for" operator in Java.


Requirements
============
 * Java 1.8 or newer.


Documentation
=============
 * [JavaDocs](https://dzavodnikov.github.io/JParFor/)
 * [Examples](https://github.com/dzavodnikov/JParFor/tree/examples/)


Use
===
Gradle
------
    ...
	allprojects {
	    ...
		repositories {
			...
			maven {
                name    'JitPack'
                url     'https://jitpack.io/' 
            }
		}
		...
	}
	...
    dependencies {
        ...
        compile 'com.github.dzavodnikov:JParFor:1.+'
        ...
    }
    ...

Maven
-----
    <project>
        ...
	    <repositories>
	        ...
		    <repository>
		        <id>JitPack</id>
		        <url>https://jitpack.io/</url>
		    </repository>
	    </repositories>
        ...
        <dependencies>
            ...
	        <dependency>
	            <groupId>com.github.dzavodnikov</groupId>
	            <artifactId>JParFor</artifactId>
	            <version>[1.0.0, 2.0.0)</version>
	        </dependency>
	        ...
	    <dependencies>
	    ...
	</project>


License
=======
Distributed under Apache License 2.0.
