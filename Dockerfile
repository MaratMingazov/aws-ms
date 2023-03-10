FROM openjdk:11

COPY target/aws-ms-*.jar aws-ms.jar

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS=""

CMD java -jar $JAVA_TOOL_OPTIONS aws-ms.jar