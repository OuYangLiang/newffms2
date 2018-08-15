FROM tomcat
ENV CATALINA_OPTS="-Djava.rmi.server.hostname=192.168.0.190 -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9000 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.rmi.port=9000"
RUN cd /usr/local/tomcat/webapps && rm -rf *
COPY ./newffms2-web/target/newffms2-web.war /usr/local/tomcat/webapps
RUN cd /usr/local/tomcat/webapps && mv newffms2-web.war ROOT.war
