Prérequis:
----------

1) Installer IntelliJ, java8, gradle
2) Installer le plugin kotlin pour intelliJ
3) Potentielemt installer un client minecraft


Compilation:
------------

1) Dans IntelliJ:

   * Import Project > rush-java/server/sources/server/build.gradle

2) Dans un terminal:

   .. code:: sh

       $ cd rush-java/server/server/
       $ java -jar BuilTools.jar
       $ cd rush-java/server/sources/server
       $ gradle shadowJar
       $ mkdir -p rush-java/server/server/plugins
       $ ln -sf rush-java/server/sources/server/build/libs/server-1.0-SNAPSHOT-all.jar rush-java/server/server/plugins/

3) Dans IntelliJ:

   * Edit configuration > Add new configuration > JAR Application
   * Path to Jar : ``rush-java/server/server/spigot-1.11.2.jar``
   * VM options: ``-Xmx2G -Xms2G -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005``
   * Working directory: ``rush-java/server/server``
   * Before Lauch > Run Maven Gloal:

     * Working directory: ``rush-java/server``
     * Command line: install
