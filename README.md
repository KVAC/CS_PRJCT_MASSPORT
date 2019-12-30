# CS_PRJCT_MASSPORT
Simple multi-threaded port scanner for address ranges. 
Dependencies:
maven

#Build:
mvn install

#Run syntax:
java -jar CS-scanner_massport-$(version)-jar-with-dependencies.jar -h=[CIDR/] -p=[port1, port2,...,portN]
example:

#Example RUN:
java -jar ./target/CS-scanner_massport-0.0.1-SNAPSHOT-jar-with-dependencies.jar -h=[12.456.789.10/16] -p=[1,3123,3,432,5,6,7,8]

#OUTPUT in file log.txt:
ip:port