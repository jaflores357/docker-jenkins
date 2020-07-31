FROM jenkins/jenkins
USER root
RUN apt-get update

RUN  apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
        software-properties-common

RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -

RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
    $(lsb_release -cs) \
    stable"

RUN  apt-get update

RUN  apt-get install -y docker-ce

COPY admin-account.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

RUN  for i in $(cat /usr/share/jenkins/ref/plugins.txt|grep -v ^#) ; do echo "########## Installing $i ############"; /usr/local/bin/install-plugins.sh $i; done

RUN chown 1000:1000 /var/jenkins_home -R

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
