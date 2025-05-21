# Rick&Morty Android App (Kotlin implemented) 

* Used the REST api to fetch the list of characters (Ktor + Coroutines)
* Stored this list to database using Room
* Returned Flow of characters from the Room DAO. WARNING: If the character has not been downloaded before, no indication of data is displayed or the character is downloaded from the API.
* Used the recommended architecture from the official Google guidelines. Used dependency injection for injecting dependencies in constructors and DI framework (Hilt). However, HttpClient and database instances are singletons.
* Fetched the character images using Coil
* Implemented searching through a special API endpoint
