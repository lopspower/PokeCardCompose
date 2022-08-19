<p align="center"><img src="/preview/header.png"></p>

PokeCardCompose
=================

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Twitter](https://img.shields.io/badge/Twitter-@LopezMikhael-blue.svg?style=flat)](http://twitter.com/lopezmikhael)

A **Pokemon Card** demo app using **Jetpack Compose** and **Koin** based on **MVI architecture**. Fetching data from the network with **Ktor** and integrating persisted data in **Room** database with usecase/repository pattern.

Preview
-----

<p align="center">
<img src="/preview/light.gif" width="270"/>
<img src="/preview/dark.gif" width="270"/>
<img src="/preview/design_system.gif" width="270"/>
</p>

Architecture
-----

<img src="/preview/clean_archi.png" width="700" height="679" />

Libraries & Tech Stack
-----

* Language: [Kotlin](https://kotlinlang.org)
* Asynchronous: [Flow](https://kotlinlang.org/docs/flow.html)
* Dependencies Injection: [Koin KSP](https://insert-koin.io)
* [Android Jetpack](https://developer.android.com/jetpack):
    * UI: [Compose](https://developer.android.com/jetpack/compose)
    * Lifecycle: [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * Navigation: [Navigation](https://developer.android.com/guide/navigation)
    * Database: [Room](https://developer.android.com/training/data-storage/room)
* Webservices: [Ktor](https://ktor.io/)
* Load Images: [Coil](https://coil-kt.github.io/coil/compose/)
* Debug: [Chucker](https://github.com/ChuckerTeam/chucker)
* Testing: [JUnit](https://developer.android.com/training/testing/instrumented-tests/androidx-test-libraries/rules)
/ [Mockito](https://github.com/mockito/mockito-kotlin)

Download
-----

If you want a more complete version of the application to manage your collection you can download **PkVintage**.

<a href="https://play.google.com/store/apps/details?id=com.mikhaellopez.pkmvintage">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

Support ❤
-----

Find this project useful? Support it by joining [**stargazers**](https://github.com/lopspower/PokeCardCompose/stargazers) for this repository ⭐️
<br/>
And [**follow me**](https://github.com/lopspower?tab=followers) for my next creations 👍

Contents Credits
-----

All copyrights of the contents, concepts, and phrases used for this open-source project belong to [The Pokemon Company](https://www.pokemon.com/).

License
-----

PokeCardCompose by [Lopez Mikhael](http://mikhaellopez.com/) is licensed under
a [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
