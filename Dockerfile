FROM docker.io/openjdk@sha256:a996cdcc040704ec6badaf5fecf1e144c096e00231a29188596c784bcf858d05
WORKDIR /app

COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=aws
ENV TZ=Asia/Seoul

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
