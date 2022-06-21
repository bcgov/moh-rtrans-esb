FROM adoptopenjdk:11-jre-hotspot

#Setting env variable. This is used for location of external properties
ENV RTRANS_HOME=/tmp

#Setting the work dir as tmp coz
WORKDIR /tmp

#Copy hns-esb jar from target folder
COPY /target/rtrans-esb.jar /tmp


#Expose is for documenting purpose. This is added for deployment support to inform the team that they will need to map this port number for access to application
#This does not expose the port in container after image is run. While running ops team has to pass the configuration to map the port
#For ex: docker run -p 4417:8080 <image name> . This will map the container's port 4417 to host's port 8080

EXPOSE 4417

#Start HNI-ESB
CMD ["java","-jar","rtrans-esb.jar"]

