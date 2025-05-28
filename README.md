# Music-Library 🎶

## Description
A modern Android music app built in Kotlin with support for English and French languages. Users can browse a music library, filter songs by artist or genre, search by name, toggle dark mode, and switch languages dynamically. Built using MVVM architecture, Room, LiveData, RecyclerView, and SharedPreferences.

## Authors
- Leen Al Harash
- [Mariyam Hanfaoui](https://github.com/hmariyam)
- [Jaskaran Singh]()

## Future Improvements
- Improve the dark mode switch to ensure a smoother transition without flickering or requiring multiple taps. Currently, toggling between themes can cause unexpected UI glitches or reinitializations.

## Diagramme Mermaid

````mermaid
erDiagram
    CHANSON o{--|| ARTISTE : produit
    CHANSON o{--|| GENRE : appartient
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

## LICENSE
- [MIT](https://choosealicense.com/licenses/mit/)
