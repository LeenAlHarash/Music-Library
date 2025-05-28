# Music-Library 🎶

## Description
A modern Android music app built in Kotlin with support for English and French languages. Users can browse a music library, filter songs by artist or genre, search by name, toggle dark mode, and switch languages dynamically. Built using MVVM architecture, Room, LiveData, RecyclerView, and SharedPreferences.


## Usage
You have two options to use this project:

**1. Fork the repository:**  
Click the "Fork" button on GitHub, then clone your fork and open it in Android Studio.

**2. Download the project files:**  
Click "Code" > "Download ZIP", extract the files, and open the project in Android Studio.


## Authors
- Leen Al Harash
- [Mariyam Hanfaoui](https://github.com/hmariyam)
- [Jaskaran Singh](https://github.com/issjayjay)


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
