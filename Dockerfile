# FROM eclipse-temurin:17-jdk-alpine
# ARG JAR_FILE
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]

#FROM eclipse-temurin:17.0.6_10-jre-alpine@sha256:c26a727c4883eb73d32351be8bacb3e70f390c2c94f078dc493495ed93c60c2f AS layers
#WORKDIR layer
#COPY --from=builder /build/target/app-0.0.1.jar app.jar
#RUN java -Djarmode=layertools -jar app.jar extract
#
FROM eclipse-temurin:17.0.10_7-jre-alpine
COPY /artifact/extracted/dependencies/ ./
COPY /artifact/extracted/spring-boot-loader/ ./
COPY /artifact/extracted/snapshot-dependencies/ ./
COPY /artifact/extracted/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
