# Required Technologies
1. mvn
2. Java 8.x - chosen for some of its appealing functionality like streams, functions and method references. 
3. Tomcat 8.x - because of the java 8 above.
4. Developed with IDEA Intellij - chosen as my IDE of choice. 
5. Spring Boot 2.0.3 - Please see requirements for this version of Spring Boot 
https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Release-Notes#third-party-library-upgrades

# Overview 

This application provides atm location creation and search REST services. It maintains a local copy of locations 
submitted through its creation service. And merges its local records with the super set from 
https://www.ing.nl/api/locator/atms/ when listing ATM locations by city.
It eliminates the duplicates by doing case field comparison during saving and when returning the 
matching location by city.

# Components

The following are some of the key components of the application.

## Database 

The application uses H2 in memory database. As this is a MVP, this database will suffice. In the future we can upgrade 
to other relational or NoSql databases. Some of the trade-off between relational and NoSql database will include 
but not limited to ACID requirements and schema changes frequency. 

## Services

The application have services that provide a secure access points to the database and external ATM location super set
service. In this version of the application, only users with the "API" role can use the services in this application. 
 
## Repository

The repository provides data services to the application services. It provides queries for creating and finding 
atm locations that are stored in the applications local database. 

## API

The application uses Apache camel to support routing service calls from the application's rest endpoints 
to both internal and external services. This design allows us to integrate our services without tightly 
coupling them or making then have direct dependencies. This also has flexibility and maintenance quality
attributes. 

## Data Models

There are two main data classes in this application, which are the AMT and ATMInfo classes. AMT represents the 
entity that is persisted in the database. ATMInfo class when instantiated is an immutable and can be used
to send information about an ATM location from the client to the server and from the server to the client. 

## Testing

The application uses a combination of integration and unit test to achieve close to a 95% coverage. The 
tests with suffix IT are integration tests and indicate tests that at some point in their processing
chain contact the super set of ATM list (https://www.ing.nl/api/locator/atms/). There are also 
unit tests that mocks this interaction using WireMock, but it always complete and appropriate to have both.


# Development and Execution

This section describes the development and execution environment and steps.  

## Build and Run Unit Test

`mvn clean install`

## Build and Run Integration Test

` mvn clean install -DskipITs=false`

## Deploy to Tomcat

` cp atm-locator.war [TOMCAT_WEBAPP]/atm-locator.war`

## Start Tomcat

`[TOMCAT_INSTALLATION_ROOT_DIR]/bin/startup.sh`

## Stop Tomcat

`[TOMCAT_INSTALLATION_ROOT_DIR]/bin/shutdown.sh`

## Run Standalone 

`mvn install spring-boot:run`

# Services and Example

This section demonstrate call to invoke the services the application provides. 

## Users

**Authorized User**: api/api <br/>
**Unauthorized User**: user/user

## API DOCUMENTATION

`http://localhost:8080/atm-locator/api/api-doc`

## JSON API: return json

### list atm in the Schiphol city on the console

`curl -u api:api http://localhost:8080/atm-locator/api/cities/Schiphol`

### Create the list of atm in the cities contained in data file. Ignoring duplicates

`curl -u api:api -X POST -H "Content-Type: application/json" -d @test_data_1.json http://localhost:8080/atm-locator/api/cities`

## HTML API : return html 

### list atm in the Schiphol city on the browser

`http://localhost:8080/atm-locator/cities/Schiphol`






