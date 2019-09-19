FROM ubi8/ubi-minimal


LABEL summary="QuoteBot" \
      description="QuoteBot" \
      io.k8s.description="QuoteBot" \
      io.k8s.display-name="QuoteBot Red Hat Forum 2019" \
      io.openshift.expose-services="8080:http" \
      io.openshift.tags="builder,scala,ubi" \
      name="gestrem/quotebot" \
      version="1.0.0" \
      release="1" \
      maintainer="guillaume estrem <gestrem@redhat.com>"
      io.openshift.s2i.scripts-url=image:///usr/local/s2i

# Copy the S2I scripts from ./.s2i/bin/ to /usr/local/s2i when making the builder image
COPY ./.s2i/bin/ /usr/local/s2i

# Copy the lighttpd configuration file
COPY ./etc/ /opt/app-root/etc

# Drop the root user and make the content of /opt/app-root owned by user 1001
RUN chown -R 1001:1001 /opt/app-root

# Set the default user for the image, the user itself was created in the base image
USER 1001

# Specify the ports the final image will expose
EXPOSE 8080

# Set the default CMD to print the usage of the image, if somebody does docker run
CMD ["usage"]
