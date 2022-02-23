# GoSecuri

1 - Les objectifs 

  Le travail demandé sur ce projet consiste à : 
  
 - Configurer un serveur web avec un accès ssh pour sa maintenance et le déploiement des fichiers 
  statiques
  
  - Ecrire d’un ou plusieurs programmes java destinés à générer les fichiers du serveur web. Leurs sources 
  seront stockés sur le dépôt git de sauvegarde
  
  - Configurer un serveur jenkins capable d’accéder au dépôt de sauvegarde et au serveur web
  
  - Concevoir un ou plusieurs pipeline(s) jenkins de génération de la ou des application(s) java
  
  - Concevoir le pipeline jenkins de génération et déploiement des fichiers du serveur web
  
L’infrastructure ajoutée est schématisée en vert sur le schéma suivant :

2 - Les cibles 

  L’application sera utilisée par l’ensemble des agents de sécurité de la société.

  
3 - Le type de solution 

  Ce projet a pour objectif de développer un système sur-mesure de vérification de l’accès à un local, ainsi qu’à 
  la visualisation de l’affectation du matériel aux agents.
  Les solutions sur-mesure permettent de proposer un système parfaitement adapté aux besoins de 
  l’utilisateur. C’est l’un des espoirs sur lesquels se repose cette demande.
  
4 - Les besoins fonctionnels 

  La solution proposée doit satisfaire d’une façon informelle les fonctionnalités suivantes :
  
  - La navigation sur les fiches des agents
  - L’actualisation des pages statiques par génération à partir des modifications du dépôt git
  - La vérification de l’accès aux fiches par authentification intégrée apache (.htpasswd)
  - La mise à jour des applications de génération par intégration continue des nouveaux sources poussés 
  sur le dépôt git.
  
5 - Les besoins non fonctionnels 
  - La disponibilité en permanence et la capacité : à tout moment le système permet de consulter les 
  fiches des agents,
  - La performance : l’application doit respecter ce critère pour assurer l’adaptation des fonctionnalités 
  répondant aux besoins des utilisateurs avec un minimum de temps de réponse,
  - L’ergonomie : les interfaces de l’application doivent être claires, intuitives, simples et conviviales et 
  on doit essayer au maximum d’éviter l’encombrement.

6 - Graphisme et ergonomie 

  6.1 La charte graphique
    La charte graphique de l’interface utilisateur devra reprendre des couleurs principales du logo de la société :
      A savoir les couleurs suivantes :
        noir (#000000) vert (#659224) bleu (#379EC1)
      Titres / Boutons :
        Ce format est utilisé pour indiquer le nom de la fonctionnalité sur laquelle l’utilisateur se trouve.
      Style : 
        Medium, majuscule à la 1ère lettre uniquement
      Texte :
        Ce format est utilisé pour tous les textes hors format spécifique.
      Style :   
        Light
        La police à utiliser sur l’application sera la police Roboto : https://www.dafont.com/fr/roboto.font
    
  6.2 Wireframes et maquettages
    Accueil
    Sur cet écran s’affiche la liste des agents par ordre alphabétique sous la forme de liens hypertextes conduisant 
    à la fiche de l’agent.
    Fiche agent
    Cette page affiche les informations liées à l’agent. 
    Les éléments de navigation ne sont pas mentionnés 
    sur la maquette, ils sont laissés au libre choix du 
    prestataire. Les informations affichées sont :
      • le nom (marqué « identification sur la 
      maquette »), 
      • l’image de la pièce d’identité de l’agent (celle 
      issue du serveur) 
      • la liste du matériel en sa possession pour le 
      mission actuelle
      
################################################################################################################################################################################
