FROM openjdk:17.0.2-jdk
VOLUME /tmp
COPY build/libs/social-commerce-0.0.1-SNAPSHOT.jar SocialCommerce.jar
ENTRYPOINT ["java","-jar","SocialCommerce.jar"]


