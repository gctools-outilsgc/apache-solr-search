# Apache Solr instance on Elgg

[![Build Status](https://travis-ci.org/Services-Sandbox/solr.svg?branch=master)](https://travis-ci.org/Services-Sandbox/solr)

[Full documentation can be found here](https://apache-solr-search.readthedocs.io/en/latest/)

**For official documentations of all the libraries that are used for this instance of Apache Solr, see the list below.**

[Apache Solr](http://lucene.apache.org/solr/guide/--)

   This is the main engine that will index the contents and store in the elgg-core directory within the data folder. Solr must have access to this core in order to store index and log warnings and errors. The configuration file is located within the solr-configuration-files directory.

[Docker Community Edition](https://docs.docker.com/install/linux/docker-ce/ubuntu/#prerequisites)
	
   This instance has been configured to run inside a Docker container that contains all the dependencies and libraries that are used. The Dockerfile can be found in docker-configuration-files.

[Kubernetes](https://kubernetes.io/docs/home/?path=users&persona=app-developer&level=foundational)
	
   Still in process to be worked on, but there are configuration files for Kubernetes deployment developed, it can be found in the kubernetes-configuration-files directory.


## Specifications and Dependencies

### Virtual Machine specifications that developed the package:
* Ubuntu Server 16 LTS
  * 2GB RAM
  * 100GB ROM
  * Virtually bridged network (to download install dependencies)
* VMware Workstation Player

### Libraries used:
* Apache Suite
  * Solr 	- the search engine
  * Tika 	- file extraction for .pdf, .docx, etc
  * Maven	- package management required to update Tika
  * Groovy	- used to retrieve and push data into solr for indexing
* Open JDK
* Docker CE
* Kubernetes
* ReactJS

## Getting Started -- Installation

### Directories Explained

| directory | description |
|-----------|-------------|
| docker-configuration-files | contains the Dockerfile needed to create and run the container for Solr |
| docs | contains the documentation for the entire project, it is currently hosted and can be viewed [here](https://apache-solr-search.readthedocs.io/en/latest/) |
| elgg-plugin | contains the plugin that can be installed on an elgg instance, it basically generates REST API that retrieves all the contents and displays it in json format |
| kubernetes-configuration-files | contains all the files required to run the project in a pod |
| search-portal-app | contains the reactjs project that formats the search results thats retrieved from solr |
| solr-configuration-files | contains the groovy scripts that will be run for indexing and the core that will be used to organize the index |

### (Optional) Manual Installation, without Docker or Kubernetes

The following needs to be installed:
- elgg

   You just need an instance of Elgg so that we have something we can crawl. The plugin for this to work is in the ```elgg-plugin``` directory which also require ```web services``` to be enabled on elgg. This basically generates REST API for the indexing portion of this project. Will require LAMP (Apache, MySQL, PHP) stack to be installed for this application. Please read up on the documentation for [elgg installation](http://learn.elgg.org/en/2.0/intro/install.html).

- reactjs

   A ReactJS project is also included in this repository in the ```search-portal-app```, so we can test the resultset that gets retrieved from Apache Solr. Please consult the **[Minimum Viable Product - Search Portal](https://github.com/Services-Sandbox/solr#optional-minimum-viable-product---search-portal)** section on installation and running the app.

- apache solr

   The meat of the repository, it just contains the configuration files. You may need to create the index folder and give the user read and write access to the entire configuration directory since the engine will be creating and modifying log files and indexes. 

   ```
   # in your home directory, download the solr package from their official site and unzip
   $ wget http://httpd-mirror.sergal.org/apache/lucene/solr/7.3.0/solr-7.3.0.tgz --no-proxy -q
   $ tar -xf solr-7.3.0.tgz 
   $ mv solr-7.3.0 solr && mv solr /opt/

   # move the configuration file (that is included in this repository) to the solr engine
   $ mv elgg-core /opt/solr/server/solr/

   # on an instance of linux, the solr app files are all located within /opt/solr/
   $ cd /opt/solr/bin

   # start the solr app using the solr user (this is recommended), unless you run root, then you will be required to add the flag -force
   $ sudo -u solr solr start
   $ sudo ./solr start -force

   Waiting up to 180 seconds to see Solr running on port 8983 [-]
   Started Solr server on port 8983 (pid=22624). Happy searching!
   ```

- apache groovy

   This is a scripting mechanism that you can run on a daily basis using crontab (linux) or scheduleder (windows), this can be found in the directory called ```solr-configuration-files```.

   ```
   # installation
   $ curl -s get.sdkman.io | bash
   $ source "$HOME/.sdkman/bin/sdkman-init.sh"
   $ sdk install groovy

   # running groovy scripts
   $ groovy script-name-here.groovy
   ```


### (Optional) Minimum Viable Product - Search Portal

This is optional, if you like to design how the results should be displayed you can use this small NodeJS site application that request and retrieves to display the result set onto the page. Since it is developed in NodeJS, it is fully dynamic. 

To get started with the ReachJS application, you need to be in the search-portal directory.

```
# install nodejs
$ curl -sL https://deb.nodesource.com/setup_8.x -o nodesource_setup.sh
$ sudo bash nodesource_setup.sh
$ apt-get install nodejs
$ npm -v

# compile the code
$ npm install -g create-react-app
$ create-react-app search-portal
$ cd search-portal/

# start the application
$ npm start

# to import libraries, they must be in the npm repository
$ npm install --save react-fontawesome
```

## Running Groovy Scripts as your indexer

```
# running groovy scripts
$ groovy your-groovy-script.groovy

# configure the crontab to run the groovy script every so often
$ crontab -e
```

File: /tmp/crontab

```
# Edit this file to introduce tasks to be run by cron.
#
# Each task to run has to be defined through a single line
# indicating with different fields when the task will be run
# and what command to run for the task
#
# To define the time you can provide concrete values for
# minute (m), hour (h), day of month (dom), month (mon),
# and day of week (dow) or use '*' in these fields (for 'any').#
# Notice that tasks will be started based on the cron's system
# daemon's notion of time and timezones.
#
# Output of the crontab jobs (including errors) is sent through
# email to the user the crontab file belongs to (unless redirected).
#
# For example, you can run a backup of all your user accounts
# at 5 a.m every week with:
# 0 5 * * 1 tar -zcf /var/backups/home.tgz /home/
#
# For more information see the manual pages of crontab(5) and cron(8)
#
# m h  dom mon dow   command

* * * * * groovy your-groovy-script.groovy

```

## Using Docker

### Prerequisites
* docker
* docker-compose

The dockerfile is in the docker-configuration-files directory, run the following commands to build and run

```
# install docker ce**
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
$ sudo add-apt-repository "deb [arch=amd-] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
$ sudo apt-get update
$ apt-cache policy docker-ce
$ sudo apt-get install -y docker-ce
$ sudo systemctl status docker

# run docker container
$ docker build -t search-portal
$ docker run --name solr-service -d -P solr-service

# display status of the container(s)
$ docker ps

# login to the container
$ docker exec -it solr-service bash

# after logging into the docker container, modify the cronjob file then start service
$ nano /etc/cron/cron.d/groovy-cron
$ service cron start
 * Starting periodic command scheduler cron                                                                                                                                         [ OK ]

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                     NAMES
6e21d435f3be        solr-service        "/opt/solr/bin/solr …"   8 seconds ago       Up 6 seconds        0.0.0.0:32768->8983/tcp   solr-service

# remove existing docker instances
$ docker kill 6e21d435f3be
$ docker rm solr-service
```

```
@TODO
- for groovy scripts, the parameters are groovy-script [site address] [solr address] [api-key]
- in the cronjob file, the addresses and api key must be changed to work in your architecture
```


## Using Kubernetes

### Prerequisites
* docker
* docker-compose
* kubectl
* kubeadm

**This is still work in progress**

```
# install kubernetes
$ sudo curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add
$ nano /etc/apt/sources.list.d/kubernetes.list
# then add the following to the file: deb http://apt.kubernetes.io/ kubernetes-xenial main

$ apt-get update
$ apt-get install -y kubelet kubeadm kubectl kubernetes-cni

$ swapoff -a 	#disable swap
# we are using flannel as our virtual network
$ kubeadm init --pod-network-cidr=10.244.0.0/16

To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

You can now join any number of machines by running the following on each node
as root:

  kubeadm join --token 3e0a6b.501f5895e6e1a9df 192.168.1.65:6443 --discovery-token-ca-cert-hash sha256:ae12de632770aa36c88cf33ae0f64a6400362a321d88a21e3612adc7a84e40b2


$ mkdir -p $HOME/.kube

# remove existing configuration, this may cause problem later on
$ rm /root/.kube/config

$ sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
$ sudo chown $(id -u):$(id -g) $HOME/.kube/config

$ sysctl net.bridge.bridge-nf-call-iptables=1

# run kubernetes pod deployment
$ kubectl portal-service --image=portal-service --port=-- --host=XXXX

# remove and clean up the service
$ kubectl kill portal-service-###
$ kubectl delete deployment portal-service

kubeadm reset
```


## Contribution

Contributions are welcomed


## Todo

- create gitignore file
- automate configurations for different ip addresses/url
- create test scripts

