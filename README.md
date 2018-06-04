# Apache Solr instance on Elgg

<style>
.alert-warning {
  color: rgb(138,109,59) !important;
}
</style>

<div class="alert-warning">Still lots of work to be done, work in progress</div>

[Full documentation can be found here](https://apache-solr-search.readthedocs.io/en/latest/)

For official documentations of all the libraries that are used for this instance of Apache Solr, see the list below.

[Apache Solr 7.3](http://lucene.apache.org/solr/guide/7_3/)

	This is the main engine that will index the contents and store in the elgg-core directory within the data folder. Solr must have access to this core in order to store index and log warnings and errors. The configuration file is located within the solr-configuration-files directory.

[Docker Community Edition](https://docs.docker.com/install/linux/docker-ce/ubuntu/#prerequisites)
	
	This instance has been configured to run inside a Docker container that contains all the dependencies and libraries that are used. The Dockerfile can be found in docker-configuration-files.

[Kubernetes](https://kubernetes.io/docs/home/?path=users&persona=app-developer&level=foundational)
	
	Still in process to be worked on, but there are configuration files for Kubernetes deployment developed, it can be found in the kubernetes-configuration-files directory.

## Specifications and Dependencies

### Virtual Machine specifications that developed the package:
* Ubuntu Server 16 LTS
  * 4GB RAM
  * 100GB ROM
  * Virtually bridged network (to download install dependencies)
* VMware Workstation 14 Player

### Libraries used:
* Apache Suite
  * Solr 	- the search engine
  * Tika 	- file extraction for .pdf, .docx, etc
  * Maven	- package management required to update Tika
  * Groovy	- used to retrieve and push data into solr for indexing
* Open JDK
* Docker CE
* Kubernetes

## Installation

### (Optional) Minimum Viable Product - Search Portal

This is optional, if you like to design how the results should be displayed you can use this small NodeJS site application that request and retrieves to display the result set onto the page. Since it is developed in NodeJS, it is fully dynamic. 

To get started with the ReachJS application, you need to be in the search-portal directory.

install nodejs

1. ```$ curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash - ```
2. ```$ apt-get install nodejs ```
3. ```$ node -v ```
4. ```$ cd /var/www/html/ ```
5. ```$ mkdir nodejs ```
6. ```$ cd nodejs/ ```
7. ```$ npm ```
7. ```$ npm install -g reactjs-portal ```

compile the code 

8. ```$ npm install -g create-react-app ```
9. ```$ create-react-app search-portal```
10. ```$ cd search-portal/ ```

start the application

11. ```$ npm start ```
12. ```$ npm install hogan.js ```
13. ```$ npm start ```
14. ```$ npm i react-router-dom --save ```
15. ```$ npm start ```


## Using Docker

The dockerfile is in the docker-configuration-files directory, run the following commands to build and run

install docker ce

1. ```$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - ```
2. ```$ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" ```
3. ```$ sudo apt-get update ```
4. ```$ apt-cache policy docker-ce ```
5. ```$ sudo apt-get install -y docker-ce ```
6. ```$ sudo systemctl status docker ```

run docker container

7. ```$ docker build -t search-portal ```
8. ```$ run --name solr-portal -d -f solr-portal ```

display status of the container(s)

9. ```$ docker ps ```


## Using Kubernetes

**This is still work in progress**

install kubernetes

1. ```$ apt-get install ```

run kubernetes pod deployment

2. ```$ kubectl portal-service --image=portal-service --port=6443 --host=192.168.1.1 ```

remove and clean up the service

3. ```$ kubectl kill portal-service-### ```
4. ```$ kubectl delete deployment portal-service ```


### Prerequisites

* docker
* docker-compose
* kubectl
* kubeadm
* (optional) nodejs


### Getting Started

## Contribution

