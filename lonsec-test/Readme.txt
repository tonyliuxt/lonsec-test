
This project is based on JDK1.7, Maven3.3

For the compile environment, have the following suppose:
1. JDK already installed
2. Maven already installed
3. Related environment variables already set

For the test project logic, have the following supposes:
1. csv file there are no "\"" need to deal
2. both memory size and disk size are enough for operate large files

For build, running unit test and generate the jar file:
1. In the root directory running "mvn test" command or "mvn install"
2. It will automatically generate a lonsec-test.jar package in ./target directory and 
3. In the directory same with lonsec-test.jar, run command "java -cp lonsec-test.jar com.lonsec.project.java.Application" 

Further:
1. The configuration file lonsec.properties can be modified accordingly
2. The test csv files can be changed accordingly.

Thanks.