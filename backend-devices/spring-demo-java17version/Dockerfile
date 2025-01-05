# Use an official Tomcat image
FROM tomcat:latest

# Copy the WAR file to Tomcat's webapps directory with a new name
COPY ./target/backend-devices.war /usr/local/tomcat/webapps/spring-demo.war

# Copy wait-for-it script and set execute permissions
COPY wait-for-it.sh /usr/local/bin/wait-for-it.sh
RUN chmod +x /usr/local/bin/wait-for-it.sh

# Expose Tomcat port 8080
EXPOSE 8080

# Start Tomcat server with wait-for-it
CMD ["/usr/local/bin/wait-for-it.sh", "devices-db:3306", "--", "catalina.sh", "run"]
