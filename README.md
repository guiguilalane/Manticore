Manticore
=========

Possibilitée de choisir entre GUI et console opérationnelle.     
Pour se faire : 
- modifier le fichier plugins.xml et ajouter un attribut gui="true/false" comme$
        Cet attribut détermine si l'on souhaite afficher une interface graphique
- modifier le fichier .properties et indiquer par une propriété gui=true/false $
        Cet attribut détermine si le plugin propose une interface graphique


HOW TO CREATE NEW PLUGINS

	1- Open Eclipse and switch to the workspace where you want to put the new plugin
	2- Create new Java Project
	3- Switch to workspace that contain Manticore project
	4- Import -> General -> Existing Projects into Workspace
	5- Browse to the workspace that contain plugins you want do add
	6- Select plugins you want to add and untick "Copy projects into workspace"
	7- And then click on "Finish"
	
	So now you can develop your plugin, and see what is going on 


Les plugins sont à ajouter dans le dossier "plugins".
Si vous voulez les modifer dans eclipse, faite un import du projet dans le même workspace que le projet Manticore sans cocher la case : "Copy projects into workspace"

Si vous avez des soucis avec l'import des dépendances de maven :
	Dans le répertoire du projet Manticore, ouvrez un terminal et tapez la commande : "mvn clean compile -U". Puis recompilez le projet.
	

