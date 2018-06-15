FROM phusion/baseimage 
LABEL maintainer="pandurx <internalfire5@live.com>"

RUN apt-get -qq update
RUN apt-get -qq -y install default-jre
RUN apt-get -qq -y install default-jdk
RUN apt-get -qq -y install wget
RUN apt-get -qq -y install unzip
RUN apt-get -qq -y install lsof
RUN apt-get -qq -y install nano
RUN apt-get -qq -y install tar
RUN apt-get -qq -y install cron
RUN apt-get -qq -y install zip
RUN apt-get -qq -y install unzip
RUN apt-get -qq -y install bash


# installing groovy
RUN cd /tmp && \
    wget https://bintray.com/artifact/download/groovy/maven/apache-groovy-binary-2.5.0.zip && \
    unzip -q apache-groovy-binary-2.5.0.zip && ls && \
    mv groovy-2.5.0 /groovy && \
    rm apache-groovy-binary-2.5.0.zip

ENV GROOVY_HOME /groovy
ENV PATH $GROOVY_HOME/bin/:$PATH
RUN /bin/bash -c "groovy -v"

RUN mkdir /usr/scripts
ADD /solr-configuration-files/groovy-scripts /usr/scripts/


# installing java
RUN mkdir /usr/java
RUN ln -s /usr/lib/jvm/java-1.8.0-openjdk-amd64 /usr/java/default
RUN mkdir /opt/solr/


# installing solr
RUN wget http://apache.mirror.gtcomm.net/lucene/solr/7.3.1/solr-7.3.1.tgz --no-proxy -q && tar -xf solr-7.3.1.tgz && mv solr-7.3.1 solr && mv solr/ /opt/

ADD /solr-configuration-files/elgg-core /opt/solr/server/elgg-core/
RUN mkdir /opt/solr/server/elgg-core/data

RUN chmod -R 777 /opt/solr/server/elgg-core/
RUN chmod -R 777 /opt/solr/bin/


# set up task scheduler
RUN touch /etc/cron.d/groovy-cron
# RUN echo '* * * * * root /groovy/bin/groovy /usr/scripts/elgg-script-delete.groovy http://192.168.1.18/gcconnex http://192.168.1.65:32768 4d09e3b4dd0276e9308cf88740a34d62923a55d9 >> /var/log/cron.log 2>&1'
ADD /solr-configuration-files/groovy-scripts/crontab /etc/cron.d/groovy-cron
RUN chmod 0644 /etc/cron.d/groovy-cron
RUN touch /var/log/cron.log

# house keeping then expose the application
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
EXPOSE 8983

CMD ["/opt/solr/bin/solr", "start", "-f", "-force"] 

