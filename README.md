# Apache Solr instance on Elgg

** WORK IN PROGRESS **


[Full documentation can be found here](https://apache-solr-search.readthedocs.io/en/latest/)

For official documentations of all the libraries that are used for this instance of Apache Solr, see the list below.

[Apache Solr --(http://lucene.apache.org/solr/guide/--)

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

### (Optional) Minimum Viable Product - Search Portal

This is optional, if you like to design how the results should be displayed you can use this small NodeJS site application that request and retrieves to display the result set onto the page. Since it is developed in NodeJS, it is fully dynamic. 

To get started with the ReachJS application, you need to be in the search-portal directory.

**install nodejs**

- ```$ curl -sL https://deb.nodesource.com/setup_-x | sudo -E bash - ```
- ```$ apt-get install nodejs ```
- ```$ node -v ```
- ```$ cd /var/www/html/ ```
- ```$ mkdir nodejs ```
- ```$ cd nodejs/ ```
- ```$ npm ```
- ```$ npm install -g reactjs-portal ```

**compile the code**

- ```$ npm install -g create-react-app ```
- ```$ create-react-app search-portal```
- ```$ cd search-portal/ ```

**start the application**

- ```$ npm start ```
- ```$ npm install hogan.js ```
- ```$ npm start ```
- ```$ npm i react-router-dom --save ```
- ```$ npm start ```


## Using Docker

### Prerequisites
* docker
* docker-compose

The dockerfile is in the docker-configuration-files directory, run the following commands to build and run

**install docker ce**

- ```$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - ```
- ```$ sudo add-apt-repository "deb [arch=amd-] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" ```
- ```$ sudo apt-get update ```
- ```$ apt-cache policy docker-ce ```
- ```$ sudo apt-get install -y docker-ce ```
- ```$ sudo systemctl status docker ```

**run docker container**

- ```$ docker build -t search-portal ```
- ```$ run --name solr-portal -d -f solr-portal ```

**display status of the container(s)**

- ```$ docker ps ```


## Using Kubernetes

### Prerequisites
* docker
* docker-compose
* kubectl
* kubeadm

**This is still work in progress**

install kubernetes

- ```$ apt-get install ```

run kubernetes pod deployment

- ```$ kubectl portal-service --image=portal-service --port=-- --host=------```

remove and clean up the service

- ```$ kubectl kill portal-service-### ```
- ```$ kubectl delete deployment portal-service ```


## Contribution

Contributions are welcomed


## Todo

- create gitignore file
- automate configurations for different ip addresses/url

