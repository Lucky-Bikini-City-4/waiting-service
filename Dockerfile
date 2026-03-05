# 1단계: 빌드 스테이지
FROM gradle:7.6-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

# 2단계: 실행 스테이지
FROM eclipse-temurin:17-jre
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]