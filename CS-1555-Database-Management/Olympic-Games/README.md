# Olympic-Games
The primary goal of this project is to implement a single Java application program that will manage the Olympic Games. At the core of this system is a databse system. The secondary goal is to learn how to develop a relatively large and real database application, also use the powerful features of SQL/PL which inslude functions procedures, and triggers. This application is implemented using Java 7, Oracle, and JDBC. This project focuses on the database component and not on the user interface.

## Initializing DB Schema / Data / Objects
1. Execute schema.sql on an Oracle SQL Server
2. Execute init.sql on the same Oracle Server
3. Execute trigger.sql on the same Oracle Server

## Compilation and Execution of Olympic.java
Compile the code using:
```javac -cp ojdbc7.jar Olympic.java```

For Windows: 
* ```javac -cp "ojdbc7.jar;." Olympic.java```

Run the code using: 
```java -cp ojdbc7.jar:. Olympic```

For Windows:
* ```java -cp "ojdbc7.jar;." Olympic```

## Method Usage 
queries.sql and Driver.java have been included as supplementatal documentation to the queries and methods used in Olympic.java. Driver.java includes helper functions and I/O information for each method. To compile Driver.java follow the following isntructions:

## Compilation and Execution of Driver.java
Compile the code using:
```javac -cp ojdbc7.jar Olympic.java Driver.java```

For Windows: 
* ```javac -cp "ojdbc7.jar;." Olympic.java Driver.java```

Run the code using: 
```java -cp ojdbc7.jar:. Driver```

For Windows:
* ```java -cp "ojdbc7.jar;." Driver```


## Author
* **Solomon Heisey**


