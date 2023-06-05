# myrecipe
My-Recipe App using Mongo
# Getting Started

This Backend API for MY-Recipe app has basic CRUD operations and filter search.
We have used here Atlas MongoDB which has great feature for auto-complete and fuzzy search based on Index.  
We have indexed Instruction column for search.  

We have used Text Search to search word in description which is faster and true text search than regex search.
    
This project uses the latest Spring-boot 3 with MongoDB. We have used lombok for injection purpose too.  
Now the poject is using  Active-Profile set to DEV and test cases also uses the DEV db

For now, there is no Security feature Enabled for the project. We can enable it by adding SecurityFilterChain with User Auth or JWT.

This is a Docker project too. We can run below commands to run a local container of the application. 

docker build -t chinmoy-recipe.jar  
  
To check image run- docker image ls  

docker run -p 8080:8080 chinmoy-recipe.jar

### API
 We have used Swagger which can be found [here](http://localhost:8080/swagger-ui/index.html#/). It has all api definations and description of all api's which is self explanatory. We have added additional api like findByRecipeName and findRecipesByVEG.

### Exception

We have used global advice controller for exception handling with custom exception classes.  
As we don't have the FRD so there are no business exceptions for business rules.
### Repository
We have used here 3 types of repository.
* MongoRepository for Spring data JPA based search conditions.
* SearchRepository for searching based on different filter conditions. It uses Mongo Template which makes it easy to do Criteria search based on fields present or not in query param.
* AtlasSearch for advanced search which is super fast and can be used for auto-completion. As atlas search is only available in Atlas Mongo we have used it for this project rather having Mongo installed locally.
[Nice article here on features of Atlas search](https://www.mongodb.com/developer/products/atlas/atlas-search-vs-regex/)

### Testing
We have used Junit 5 for testing. Currently, we have for Controller class and incomplete.
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#data.nosql.mongodb)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

