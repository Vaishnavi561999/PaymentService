FROM gradle:7.1 AS BUILD
RUN mkdir /app
COPY . /app/
WORKDIR /app
RUN chmod +x gradlew
RUN gradle installDist
FROM openjdk:11 AS RUN
RUN mkdir /app
RUN useradd -u 1000 tajhotels
COPY --from=BUILD /app/build/install/com.ihcl.payment-service/ /app/
COPY ./agentlib/applicationinsights-agent-3.4.17.jar agent.jar
COPY ./agentlib/applicationinsights.json applicationinsights.json
ENV JAVA_OPTS="-javaagent:/agent.jar"
COPY PrivateKey.pem /app/bin/
WORKDIR /app/bin
RUN mkdir docs
RUN chmod +x /app/bin && chown -R tajhotels:tajhotels /app/bin/docs
EXPOSE 8083:8083
USER tajhotels
CMD ["./com.ihcl.payment-service"]
