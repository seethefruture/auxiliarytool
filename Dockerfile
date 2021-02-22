FROM java:8
COPY target /app
CMD java -jar /app/auxiliarytool-0.0.1-SNAPSHOT.jar
EXPOSE 8080
