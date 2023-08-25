FROM adoptopenjdk/openjdk8:alpine-slim
ADD target/rogue-inventory-1.0-SNAPSHOT-jar-with-dependencies.jar rogue-inventory.jar
ENTRYPOINT ["java", "-jar", "/rogue-inventory.jar"]
