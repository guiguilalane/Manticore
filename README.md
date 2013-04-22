Manticore
=========

Possibilitée de choisir entre GUI et console opérationnelle.     
Pour se faire : 
- modifier le fichier plugins.xml et ajouter un attribut gui="true/false" comme$
        Cet attribut détermine si l'on souhaite afficher une interface graphique
- modifier le fichier .properties et indiquer par une propriété gui=true/false $
        Cet attribut détermine si le plugin propose une interface graphique



Les plugins sont à ajouter dans le dossier "plugins".
Si vous voulez les modifer dans eclipse, faite un import du projet dans le même workspace que le projet Manticore sans cocher la case : "Copy projects into workspace"

Si vous avez des soucis avec l'import des dépendances de maven :
	Dans le répertoire du projet Manticore, ouvrez un terminal et tapez la commande : "mvn clean compile -U". Puis recompilez le projet.
	

