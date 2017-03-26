CR33PS
------

`Boom` He said.

1. Téléchoper le git (creepita sur github).
2. Téléchoper et installer gradle, java8, intellij idea, le plugin kotlin etc...
3. Potentielemt téléchoper un client minecraft.
4. Importer le projet comme un projet gradle.
5. Dans un terminal, dans le dossier `work` du projet, lancer la commande `java -jar BuildTools.jar` pour
téléchoper/compiler spigot.
6. Lancer une fois la compilation du projet (gradle shadowJar).
7. Créer un lien symbolique depuis le jar construit (./build/libs/creeps-1.0-SNAPSHOT-all.jar) vers le dossier
work/plugins (pour éviter de cp le jar a chaque compile... - possible que vous ayez a créer le dossier plugin si 1st
run).
8. Créer une configuration de lancement intellij de type "jar application" avec les infos suivantes:
  - Path to jar: ${project_root}/work/spigot-1.9.jar (remplacez ${project_root} par votre racine hein...).
  - VM options: -Xmx2G -Xms2G -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
  - Working directory: pointez vers ${project_root}/server/
  - Before Lauch: maven task >> install

9. A chaque fois que vous {re}lancerais cette conf, il va recompiler le nécessaire, mettre a jour la jar et démarer le
 serveur.

10. Plus qu'a coder une IA :p. Ou utiliser n'importe quel outil rest pour tester.
11. Pour voire le "protocole", tout est dans la méthode listen du CreepsPlugin.
