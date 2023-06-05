FROM openjdk:17
EXPOSE 8080
ADD target/chinmoy-recipe.jar chinmoy-recipe.jar
ENTRYPOINT ["java","-jar","/chinmoy-recipe.jar"]