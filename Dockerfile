FROM tomcat:9

ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-dev}
ENV TZ=Asia/Ho_Chi_Minh

RUN rm -rf /usr/local/tomcat/webapps/*
COPY . /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]
EXPOSE 8080
