# FROM eclipse-temurin:17.0.10_7-jre-alpine
# ARG JAR_FILE
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]

#
FROM eclipse-temurin:17.0.10_7-jre-alpine
COPY /artifact/extracted/dependencies/ ./
COPY /artifact/extracted/spring-boot-loader/ ./
COPY /artifact/extracted/snapshot-dependencies/ ./
COPY /artifact/extracted/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
