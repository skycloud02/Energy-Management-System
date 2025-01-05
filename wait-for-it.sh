# Use an official Tomcat image
FROM tomcat:latest

# Copy the WAR file or app files into Tomcat's webapps directory
COPY ./target/backend-person.war /usr/local/tomcat/webapps/

# Copy wait-for-it script
COPY wait-for-it.sh /wait-for-it.sh

# Expose Tomcat port 8080
EXPOSE 8080

# Start Tomcat server with wait-for-it
CMD ["./wait-for-it.sh", "city-db:3306", "--", "catalina.sh", "run"]