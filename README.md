# TheNewsApp
Sandbox repository to play around with Android and Kotlin latest goodies.

# Basics
The News app is a Master / Detail flow app in which the user can search for news and select any news to see its detail.

**Note:** to compile and being able to search for news you need to get an API key from https://newsapi.org and add it in the `gradle.properties` file, i.e.: `NEWS_API_KEY=“YOUR-API-KEY”`


https://user-images.githubusercontent.com/5704827/194128666-1d61b373-19f5-4684-9eed-10bcacd8d802.mov

# Key concepts
* Layers: the app is organized in the following layers:
	* Data layer: provides components to access data from database and network. DAO and Repository patterns are being used here. Also, the `DataModule` provides dependencies for all layers using Hilt.
	* Domain layer: provides use cases to handle the business logic.
	* Presentation layer: provides components to display data on the screen and to handle user’s interaction.
* Feature package: in the presentation layer the feature packages are:
	* `shownews`: fragment, adapter and view model to show a list of news.
	* `newsdetai`: fragment to show the news detail.
* MVVM architecture pattern with Data Binding.
* Shared View Model between `ShowNewsFragment` and `NewsDetailFragment`.
* Coroutines to execute code asynchronously.
* Hilt for dependency injection.
* Retrofit for networking.
* Glide for image loading.
* Room for persistence.
* JUnit and Mockito for unit testing.
* Navigation with safe arguments.
* All dependencies to libs/tools and third-party libs are in `versions.gradle` file.
