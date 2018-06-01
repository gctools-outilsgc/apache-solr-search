# apache-solr-search

For documentation, please visit https://apache-solr-search.readthedocs.io/en/latest/

For documentation respective to all the dependencies, please visit their official site for full documentations. This repository will only contain short summaries of information pertaining to minor modification to configuration files.
Apache Solr 7.3: http://lucene.apache.org/solr/guide/7_3/
Docker CE: https://docs.docker.com/install/linux/docker-ce/ubuntu/#prerequisites
Kubernetes: https://kubernetes.io/docs/home/?path=users&persona=app-developer&level=foundational

Virtual Machine specifications that developed the package:
* Ubuntu Server 16 LTS
  * 4GB RAM
  * 100GB ROM
  * Virtually bridged network (to download install dependencies)
* VMware Workstation 14 Player

Libraries used:
* Apache Suite
  * Solr 	- the search engine
  * Tika 	- file extraction for .pdf, .docx, etc
  * Maven	- package management required to update Tika
  * Groovy	- used to retrieve and push data into solr for indexing
* Open JDK
* Docker CE
* Kubernetes


