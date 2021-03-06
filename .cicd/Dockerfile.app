FROM hax0rcorp/java11gradle:latest

COPY . /app
WORKDIR /app

RUN gradle build && \
    mv builds/libs/*.jar builds/build.jar

EXPOSE 8080 9000
CMD ["java","-Dspring.profiles.active=dev", "-jar", "builds/build.jar"]
