FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM eclipse-temurin:17.0.6_10-jre-alpine@sha256:c26a727c4883eb73d32351be8bacb3e70f390c2c94f078dc493495ed93c60c2f AS layers
#WORKDIR layer
#COPY --from=builder /build/target/app-0.0.1.jar app.jar
#RUN java -Djarmode=layertools -jar app.jar extract
#
#FROM eclipse-temurin:17.0.6_10-jre-alpine@sha256:c26a727c4883eb73d32351be8bacb3e70f390c2c94f078dc493495ed93c60c2f
#WORKDIR /opt/app
#RUN addgroup --system appuser && adduser -S -s /usr/sbin/nologin -G appuser appuser
#COPY --from=layers /layer/dependencies/ ./
#COPY --from=layers /layer/spring-boot-loader/ ./
#COPY --from=layers /layer/snapshot-dependencies/ ./
#COPY --from=layers /layer/application/ ./
#RUN chown -R appuser:appuser /opt/app
#USER appuser
#HEALTHCHECK --interval=30s --timeout=3s --retries=1 CMD wget -qO- http://localhost:8080/actuator/health/ | grep UP || exit 1
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]