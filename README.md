# iris-java9
IRIS Email Client using Java 9 modular system


## pre requisites

Install a Java Early Access Release. You have basically two options:

  * Download the JDK manually:

[JDK 9 Early Access with Project Jigsaw](https://jdk9.java.net/jigsaw/)

  * Use a package manager (ubuntu):

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java9-installer
```

### about java 9

Here are few links to get started:

* Articles:

http://openjdk.java.net/projects/jigsaw/quick-start

http://openjdk.java.net/projects/jigsaw/spec/sotms/

https://www.infoq.com/articles/Latest-Project-Jigsaw-Usage-Tutorial

* Videos:

[Devoxx - Prepare for JDK 9! by Mark Reinhold/Alan Bateman] (https://www.youtube.com/watch?v=KZfbRuvv5qc)

[JavaOne - CON5107 Prepare for JDK 9] (https://www.youtube.com/watch?v=nBAUaOoBdGU)

[JavaOne - CON5118 Introduction to Modular Development] (https://www.youtube.com/watch?v=a99RmjgG5Eo)

[Devoxx - Introduction to Modular Development by Mark Reinhold/Alan Bateman] (https://www.youtube.com/watch?v=qr4O4SbzihQ)

[JavaOne - CON6821 Advanced Modular Development] (https://www.youtube.com/watch?v=YPQ2V-hQb8w)

[JavaOne - CON6823 Project Jigsaw Under the Hood] (https://www.youtube.com/watch?v=xswtIp730Ho)

[JavaOne - CON6826 Ask the Architects] (https://www.youtube.com/watch?v=jAL72EhLTXo)

[JavaOne - TUT6825 Project Jigsaw Hack Session] (https://www.youtube.com/watch?v=r2DeuDCCywM)


## dependencies

Dependencies, for now, will be downloaded 'manually', as declared on pom.xml

The dependencies should be selected/declared manually according to your modules selection

Then run:

`mvn package`

to download jar libs to a specific folder, that will be used as an automatic module.

Example: ... 


## build

`build.sh`


## usage
