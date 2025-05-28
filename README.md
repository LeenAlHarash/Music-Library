# Audio Library - TP2 

## Membres de l'équipe Colibri 🕊
- Leen Al Harash
- Mariyam Hanfaoui
- Jaskaran Singh

## Mode de communication 🌎
- Discord
- Teams

## Lien vers le projet 🔗
- [TP2 - Colibri](https://git.dti.crosemont.quebec/lal/tp2-colibri)

## La personne contacte de l'équipe 🙎‍♀️
- Leen Al Harash

## Le type d'application 📱
- Gestionnaire de listes de lecture musicales

## Description de chaque fonctionnalité et son responsable 
| Personne responsable | Description détaillée de la fonctionnalité |
| ------------- | ------------- |
| Leen | - Création de la page Profile<br/> - Création de la page accueil<br /> - Internalisation de ces deux pages<br/> - Création de la page librarie<br /> - Création de la page formulaire<br/> - Liaison entre toutes les pages<br/> - Supprimer une chanson<br /> - Tests Unitaires (supprimer)|
| Mariyam |  - Internalisation du formulaire<br /> - Ajouter une chanson <br/> - Modification d'une chanson<br /> - Tests Unitaires (Modifier et ajouter un chanson)<br /> - Création des tables SQL<br/> - Création de diagramme Mermaid|
| Jaskaran | - Afficher la liste des chansons (recyclerView)<br /> - Internalisation des chansons (librarie)<br /> - Fonction Recherche par nom de la chanson<br />  - Tests Unitaires (pour rechercher)<br /> - Filtre par genre et par artist<br /> - Thème sombre et clair|

# Diagramme Mermaid - AudioLibrary

````mermaid
erDiagram
    CHANSON o{--|| ARTISTE : a
    CHANSON o{--|| GENRE : a
    CHANSON {
        int id
        string nom
        int artisteId
        int genreId
    }
    ARTISTE {
        int id
        string nom
    }
    GENRE { 
        int id
        string nom
    }
````