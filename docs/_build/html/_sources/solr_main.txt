Apache Solr Search
==================

Overview and Summary
--------------------

Documentation can also be referenced in the `Github repository <https://github.com/gctools-outilsgc/apache-solr-search>`_

Official Documentatioon for some of the components mentioned in this documentation:

* `Apache Solr <http://lucene.apache.org/solr/guide/7_3>`_
* `Docker Community Edition <https://docs.docker.com/install/linux/docker-ce/ubuntu/#prerequisites>`_
* `Kubernetes <https://kubernetes.io/docs/home/?path=users&persona=app-developer&level=foundational>`_

This entire project will contain small components that can be used for testing as minimal viable products. It is not required to have everything running, the main files that this project will use will be in the solr-configuration-files/:

* /elgg-core/ contains the solr core configuration files that is required to be placed within /opt/solr/server/solr/ and will need the directory /index/ to be created. The entire directory will need to be accessed by solr, root access can be possible if the instance is created in the development environment.
* /groovy-scripts/ contains the script that will retrieve json data from the web application through REST API and push it into the Solr search engine to create index
* /jar-files/ contains missing jar files that solr may be missing, there may be some errors that could come up after a fresh install of the engine

There are also other configuration files to be considered if the instance will be deployed as either in a Kubernetes pod or as Docker containers, which can be found in the following:

* /docker-configuration-files/ contains the Dockerfile that will be required to deploy containers
* /kubernetes-configuration-files/ contains the kubernetes configuration files that will be required to deploy in a pod

The idea for this project was converting the Solr instance into service which will then be managed in Kubernetes. This service will require access to files and REST API in order to index the content on the site as well as being able to search the contents within various documents (including .pdf, .doc, .docx, etc). The way it works is that the REST API will provide a more accurate content data without scraping an entire web page with all the excess JavaScript and HTML code and pushed to the Solr instance for indexing. This will help with results manipulation when it is displayed on the front end (can be tested using the ReactJS application) as well as retrieving data that would only be available to the user (if access roles are implemented). Please see the groovy scripts, the elgg plugin solr_api for a more in depth technical information, and the diagram below for the workflow.

.. image:: images/basic_server_architecture.PNG

In the diagram above, we have the web application server that is connected to both the database server and FTP file server. The web application server will contain the REST API that will be used to retrieve data from either the database and links to files from the FTP server. The APIs (implemented as an Elgg plugin) require authentication since these information could be set to specific access roles by the content owner.

The Groovy scripts that will retrieve the data from the API, manipulate and clean (removing JavaScript and HTML, organizing the data to conform to the Solr schema configuration, etc) the information before it is push and commited to the Solr instance for indexing. The said scripts will be managed on the dedicated search server, which will be run on a daily basis as a crontab on Linux, or scheduler on Windows.

::

    @TODO

    - Groovy scripts allow parameter to specify Solr and REST API URL or IP Address
    - Create snippet of commands to start up Kubernetes, Docker, or manual set up
    - Develop more in depth workflow diagram (data flow)


Dependencies and Requirements
-----------------------------
This project was developed on Ubuntu Server 16 LTS, on VMWare Workstation 14 Player and tested on the Azure Cloud (excluding Kubernetes, which are managed differently on the cloud than on the local instance). There may be additional steps that are required to run it on a Windows environment.

Since there is a Dockerfile included within this repository, all the dependencies will be automatically included into the project.
The dockerfile includes the following dependencies (the versions may change over time). 
This instance of Solr within a Docker and orchestrated by local instance of Kubernetes was developed on Ubuntu Server 16 LTS. This docker repository can be found at https://hub.docker.com/r/pandurx/solr-service/.


+---------------------------------+---------------------------------------------------------------------+
| Virtual Machine Specifications  |        Dependencies                                                 |
+=================================+=====================================================================+
| * Ubuntu Server 16 LTS          |        +---------------+---------+--------------------------------+ |
| * 4GB RAM                       |        | Library       | Version | Link                           | |
| * 100GB ROM                     |        +===============+=========+================================+ |
| * VMware Workstation 14 Player  |        | Apache Solr   | 7.3.0   | http://lucene.apache.org/solr/ | |
|                                 |        +---------------+---------+--------------------------------+ |
|                                 |        | Apache Groovy | 3.0.0   | http://groovy-lang.org         | |
|                                 |        +---------------+---------+--------------------------------+ |
|                                 |        | Apache Tika   | 1.17    | https://tika.apache.org/       | |
|                                 |        +---------------+---------+--------------------------------+ |
|                                 |        | Apache Maven  | 3.5.2   | https://maven.apache.org/      | |
|                                 |        +---------------+---------+--------------------------------+ |
|                                 |        | Open JDK      | 1.8.0   | http://openjdk.java.net/       | |
|                                 |        +---------------+---------+--------------------------------+ |
|                                 |        | Docker CE     |         | https://www.docker.com/        | |
|                                 |        +---------------+---------+--------------------------------+ |
|                                 |        | Kubernetes    |         | https://kubernetes.io/         | |
|                                 |        +---------------+---------+--------------------------------+ |
|                                 |        |               |         |                                | |
|                                 |        +---------------+---------+--------------------------------+ |
|                                 |                                                                     |
+---------------------------------+---------------------------------------------------------------------+
| Updated on June 1 2018                                                                                |
+-------------------------------------------------------------------------------------------------------+

::

    @TODO

    - Implementation on new versions of Ubuntu Server (or other distribution)
    - Implementation on Windows environment


Installation and Implementation
-------------------------------
Maecenas vehicula dapibus odio, in faucibus dolor fringilla nec. Mauris vestibulum urna erat, a sodales est viverra at. Sed libero nulla, sodales nec maximus vel, pretium quis tortor. Fusce tellus eros, vehicula ut imperdiet mollis, finibus iaculis enim. Donec id mi mauris. Suspendisse molestie elementum feugiat. Nullam et interdum odio, vestibulum placerat arcu. Fusce laoreet, lacus ut ullamcorper mollis, leo odio gravida ex, pulvinar vestibulum felis tellus ac arcu. Integer nec enim cursus, tristique risus quis, volutpat velit. Aenean pretium elit eros, non hendrerit mauris euismod ac. Ut vitae posuere tellus, nec lobortis ligula. Duis arcu mi, suscipit non dui vel, ullamcorper imperdiet lectus.


Installing Docker
^^^^^^^^^^^^^^^^^
Nam ut risus id ipsum blandit semper in sit amet ipsum. Ut tincidunt, justo at sagittis venenatis, turpis enim blandit metus, sit amet tempus lacus ex in turpis. Phasellus vitae aliquet quam, ultrices laoreet sapien. Cras varius ipsum nec enim porttitor sagittis. Quisque varius lacus pellentesque accumsan vestibulum. Integer nisi risus, maximus ut blandit in, finibus non nunc. Ut id eros et erat laoreet ornare malesuada sed risus. Mauris efficitur porttitor tempus. Donec odio neque, bibendum ac tempus non, viverra ac quam. Mauris malesuada luctus ultricies. Donec non lacus sagittis, fermentum libero ut, venenatis sapien. Aliquam semper varius orci. Donec eu odio porttitor, bibendum metus ullamcorper, hendrerit dui. Aenean sed placerat neque, in posuere nisl.

Nullam sed sagittis lorem. Curabitur mattis ut quam sit amet ultricies. Praesent eget tristique ex. Sed dignissim mauris nec nisi feugiat, eu euismod odio semper. Maecenas quis posuere ante. Sed quis egestas lorem. Aliquam non arcu sit amet leo dapibus interdum. Vestibulum id blandit eros, id porttitor erat. Pellentesque vitae ipsum et risus ornare vulputate aliquet vulputate nisi. Quisque sed faucibus magna, a aliquam mauris. Nulla sapien ligula, dapibus sed venenatis laoreet, rhoncus vitae quam. Suspendisse nec quam ultrices, rhoncus lacus nec, cursus lorem. Mauris maximus quam ante, hendrerit porta nulla porta quis. Fusce porta diam ut diam imperdiet imperdiet.


Installing Kubernetes (local virtual machine, optional)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Nam ut risus id ipsum blandit semper in sit amet ipsum. Ut tincidunt, justo at sagittis venenatis, turpis enim blandit metus, sit amet tempus lacus ex in turpis. Phasellus vitae aliquet quam, ultrices laoreet sapien. Cras varius ipsum nec enim porttitor sagittis. Quisque varius lacus pellentesque accumsan vestibulum. Integer nisi risus, maximus ut blandit in, finibus non nunc. Ut id eros et erat laoreet ornare malesuada sed risus. Mauris efficitur porttitor tempus. Donec odio neque, bibendum ac tempus non, viverra ac quam. Mauris malesuada luctus ultricies. Donec non lacus sagittis, fermentum libero ut, venenatis sapien. Aliquam semper varius orci. Donec eu odio porttitor, bibendum metus ullamcorper, hendrerit dui. Aenean sed placerat neque, in posuere nisl.

Nullam sed sagittis lorem. Curabitur mattis ut quam sit amet ultricies. Praesent eget tristique ex. Sed dignissim mauris nec nisi feugiat, eu euismod odio semper. Maecenas quis posuere ante. Sed quis egestas lorem. Aliquam non arcu sit amet leo dapibus interdum. Vestibulum id blandit eros, id porttitor erat. Pellentesque vitae ipsum et risus ornare vulputate aliquet vulputate nisi. Quisque sed faucibus magna, a aliquam mauris. Nulla sapien ligula, dapibus sed venenatis laoreet, rhoncus vitae quam. Suspendisse nec quam ultrices, rhoncus lacus nec, cursus lorem. Mauris maximus quam ante, hendrerit porta nulla porta quis. Fusce porta diam ut diam imperdiet imperdiet.


Commands Explained
------------------
Maecenas vehicula dapibus odio, in faucibus dolor fringilla nec. Mauris vestibulum urna erat, a sodales est viverra at. Sed libero nulla, sodales nec maximus vel, pretium quis tortor. Fusce tellus eros, vehicula ut imperdiet mollis, finibus iaculis enim. Donec id mi mauris. Suspendisse molestie elementum feugiat. Nullam et interdum odio, vestibulum placerat arcu. Fusce laoreet, lacus ut ullamcorper mollis, leo odio gravida ex, pulvinar vestibulum felis tellus ac arcu. Integer nec enim cursus, tristique risus quis, volutpat velit. Aenean pretium elit eros, non hendrerit mauris euismod ac. Ut vitae posuere tellus, nec lobortis ligula. Duis arcu mi, suscipit non dui vel, ullamcorper imperdiet lectus.


If you are currently logged in as root user on the linux machine, you might run into an error stating that you cannot start the solr service as that user. Typically, it is much safer to run the solr server with a different user account that has only access necessary directories and the access rights to run specific services that is required for the search functionality.

If the search engine is currently in development or testing, the service can be started with a root user with an additional parameter passed in. Otherwise, omit the ``-f`` flag if the service will be started with a non-root user.


**Please take note that the script to run the engine is in the ``/opt/solr/bin`` directory. The example below assumes that the the solr script is in the current directory**

running solr as a root user

``root@ubuntu:~# ./solr start -f``

``root@ubuntu:~# ./solr stop``

``root@ubuntu:~# ./solr restart -f``

running solr as a non-root user

``solr@ubuntu:~# ./solr start``


 
**Inline Markup**
-----------------
Words can have *emphasis in italics* or be **bold** and you can define
code samples with back quotes, like when you talk about a command: ``sudo`` 
gives you super user powers!