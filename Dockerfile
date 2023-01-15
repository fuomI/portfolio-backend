# Specify a base image
FROM amazoncorretto:17-alpine

# Specify the workdir
WORKDIR /usr/app

# Copy WAR
COPY ./target/portfolio-backend.war ./

# Default command
# CMD ["java", "-jar", "/usr/app/portfolio-backend.war"]