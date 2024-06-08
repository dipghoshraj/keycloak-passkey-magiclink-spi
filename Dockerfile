FROM quay.io/keycloak/keycloak:23.0.7 as builder

COPY target/custom-authentication-1.0.2.jar /opt/keycloak/providers/

ENV KC_HEALTH_ENABLED=true \  
    KC_METRICS_ENABLED=true \  
    KC_FEATURES=token-exchange


RUN /opt/keycloak/bin/kc.sh build


FROM quay.io/keycloak/keycloak:latest

# Set environment variables
ENV KEYCLOAK_USER=admin
ENV KEYCLOAK_PASSWORD=admin

# Copy the custom required action JAR into the Keycloak providers directory
COPY --from=builder /opt/keycloak/ /opt/keycloak/


# Expose the necessary ports
WORKDIR /opt/keycloak

ENV NAMESPACE=keycloak \
    APPLICATION_NAME=keycloak \  
    KC_PROXY=edge

# ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]


# docker run -p 8080:8080 --name keycloak-custom -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin keycloak-custom:latest