# TheNewsApp
Sandbox repository to play around with Android and Kotlin latest goodies.

# Basics
The News app is a Master / Detail flow app in which the user can search for news and select any news to see its detail.

**Note:** to compile and being able to search for news you need to get an API key from https://newsapi.org and add it in the `gradle.properties` file, i.e.: `NEWS_API_KEY=“YOUR-API-KEY”`

# Features
1. Search news by a given query

![news](https://github.com/lucaslabs/TheNewsApp/assets/5704827/ccea2396-69fe-44b5-88d7-64be4df97113)

2. Offline-first approach
   
![news offline](https://github.com/lucaslabs/TheNewsApp/assets/5704827/66e02c86-8d71-4fc2-83f0-70e1c0ce87b7)

# Key concepts
* Offline-first approach, this is, the database is the source of truth.
* Clean architecture layers:
   * Layers: the app is organized into the following layers:
   * Data layer: provides components to access data from the database and network. DAO and Repository patterns are being used here. 
     Also, the DataModule provides dependencies for all layers using Hilt.
   * Domain layer: provides use cases to handle the business logic.
   * Presentation layer: provides components to display data on the screen and to handle user interaction.
* Feature package: in the presentation layer the feature packages are:
   * searchnews: composable functions and view model to search for news by a given query and then to show the result list of news.
   * newsdetail: composable function to show the news detail.
* MVVM architecture pattern with Use Cases.
* Coroutines to execute code asynchronously.
* Flow to push data between layers.
* Jetpack Compose to create UI components in a declarative way.
* Hilt for dependency injection.
* Retrofit for networking.
* Coil for image loading.
* Room for persistence.
* Navigation with go to different screens.
* JUnit, MockK, and Turbine for unit testing.
* All dependencies to libs/tools and third-party libs are in versions.gradle file.
