FROM openjdk:8-jdk

COPY ./medicine_backend-0.0.1-SNAPSHOT.jar /usr/local

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/shanghai" > /etc/timezone;

ENTRYPOINT ["java", "-jar", "/usr/local/medicine_backend-0.0.1-SNAPSHOT.jar"]
