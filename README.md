# Git User Search

The App uses a set of Android Jetpack libraries plus Retrofit to search users and display its details by using GitHUB API. The App uses Kotlin.

### Prerequisites

The project has all required dependencies in the gradle files. 
Add the Project to Android Studio or Intelij and build.
All the required dependencies will be downloaded and installed.

#### Important : You need to add the token you got from GitHub in the api.properties file.

## Architecture

The project uses MVVM architecture pattern.

## Libraries 

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/) - Manage UI related data in a lifecycle conscious way and act as a channel between use cases and ui
* [DataBinding](https://developer.android.com/topic/libraries/data-binding) - support library that allows binding of UI components in layouts to data sources,binds character details and search results to UI
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Android Jetpack's Navigation component helps in implementing
navigation between fragments
* [Hilt](https://dagger.dev/hilt/) - For Dependency Injection.
* [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=in) - Allow pagination of the Data.
* [Retrofit](https://square.github.io/retrofit/) - To access the Rest Api
* [Mockk](https://mockk.io/) - To mocking objects for testing

## Screenshots
|<img src="screenshots/1.png" width=200/>|<img src="screenshots/2.png" width=200/>|<img src="screenshots/3.png" width=200/>|<img src="screenshots/5.png" width=200/>


Developed by Emre Yasar [`@emreyasar`](https://www.linkedin.com/in/emreyasar/)
