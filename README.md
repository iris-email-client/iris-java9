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

[Project Jigsaw] (http://openjdk.java.net/projects/jigsaw/)

[Project Jigsaw: Module System Quick-Start Guide] (http://openjdk.java.net/projects/jigsaw/quick-start)

[Project Jigsaw: Exploratory Phase] (http://openjdk.java.net/projects/jigsaw/exploratory-phase)

[The State of the Module System] (http://openjdk.java.net/projects/jigsaw/spec/sotms/)

[JEP 200: The Modular JDK] (http://openjdk.java.net/jeps/200)

[Programming with Modularity and Project Jigsaw] (https://www.infoq.com/articles/Latest-Project-Jigsaw-Usage-Tutorial)

[Managing in an Age of Modularity] (https://hbr.org/1997/09/managing-in-an-age-of-modularity/ar/1)

[OSGi and Java 9 Modules Working Together] (http://njbartlett.name/2015/11/13/osgi-jigsaw.html)

[Java 9 Module System] (http://blog.osgi.org/2015/10/java-9-module-system.html)

[Modularity in Java 9] (http://www.javaworld.com/article/2878952/java-platform/modularity-in-java-9.html)

* Videos: 

[JavaOne - CON5107 Prepare for JDK 9] (https://www.youtube.com/watch?v=nBAUaOoBdGU) or [Devoxx - Prepare for JDK 9! by Mark Reinhold/Alan Bateman] (https://www.youtube.com/watch?v=KZfbRuvv5qc)

[JavaOne - CON5118 Introduction to Modular Development] (https://www.youtube.com/watch?v=a99RmjgG5Eo) or [Devoxx - Introduction to Modular Development by Mark Reinhold/Alan Bateman] (https://www.youtube.com/watch?v=qr4O4SbzihQ)

[JavaOne - CON6821 Advanced Modular Development] (https://www.youtube.com/watch?v=YPQ2V-hQb8w)

[JavaOne - CON6823 Project Jigsaw Under the Hood] (https://www.youtube.com/watch?v=xswtIp730Ho)

[JavaOne - CON6826 Ask the Architects] (https://www.youtube.com/watch?v=jAL72EhLTXo)

[JavaOne - TUT6825 Project Jigsaw Hack Session] (https://www.youtube.com/watch?v=r2DeuDCCywM)



## build and run

run the bash script (set specific variables before using) to build/test/run:

`./new_build.sh`



## usage

Create 'iris.properties' file in $USER_HOME/.iris 

Example (/home/pedro/.iris/iris.properties):

```
language = 

##default provider
provider = gmail
username = XXX@gmail.com
password = XXX

##security keys (uncomment/define if using gpg)
#gpg.file.private=/home/pedro/.gnupg/secring.gpg
#gpg.file.public=/home/pedro/.gnupg/pubring.gpg
#gpg.file.secret=123456
```

'language' property can be left blank to use the defualt language or defined to a specific language like 'language = pt_BR'

