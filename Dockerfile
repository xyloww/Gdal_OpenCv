# Use the official OpenJDK image as a parent image
FROM openjdk:17-bullseye

# Set the working directory in the container
WORKDIR /app

# Copy the .jar file from your host to your current location in the image
COPY build/libs/*.jar app.jar
COPY land_shallow_topo_8192.tif land_shallow_topo_8192.tif

RUN apt-get update
RUN apt-get -y install libgdal-dev
RUN apt-get -y install gdal-data
RUN apt-get -y install gdal-bin
RUN gdalinfo --version

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file 
ENTRYPOINT ["java","-jar","app.jar"]
