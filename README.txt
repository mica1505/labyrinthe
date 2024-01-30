Indications d'execution
==================
# miniProjet
## Execution de la classe principale
Pour lancer le programme il suffit d'executer le fichier Resolution.java et mettre le chemin absulu du fichier qui contient le labyrinthe comme demande dans la console.
## Execution depuis le jar
se deplacer dans le repertoire qui contient le jar executable et lancer la commande 
	java -jar MAZOUZCamelia_miniProjetRunnableJar.jar
Le fichier depuis lequel on récupère le graphe doit respecter le format suivant : 
<<<<<Nombre d'instances suivis du nombre de lignes, suivi du nombre de colonnes, suivi du labyrinthe.(separes avec des retours a la ligne)>>>>
1
4
4
.D..
...F
.S..
####

### Remarques
si le prisonnier et le feu arrivent a la sortie au meme moment, le prisonnier perd

--------------------------------------------
# Exemple d'execution du labyrinthe
--------------------------------------------
>>java -jar MAZOUZCamelia_miniProjetRunnableJar.jar
Veuillez saisir le chemin absolu du fichier qui contient le labyrinthe : 
C:\Users\pc\Documents\L3\S5\Algo\Partie2\miniProjet\src\labyrinthe.txt
....D
.....
.....
F...S

...D
....
....
F..S

###D
####
S..F

.D..
...F
.S..
####

.D..
....
.S..
...F

Labyrinthe n_1
Y
Labyrinthe n_2
Oups, le feu arrive a la sortie avant le prisonnier!
N
Labyrinthe n_3
Il n'y a pas de solution!!
N
Labyrinthe n_4
Y
Labyrinthe n_5
Y