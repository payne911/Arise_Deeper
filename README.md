# Arise Deeper
A 2D roguelike game, with top-down view. Developed using the LibGDX framework.


# Demo
![early_demo](https://raw.githubusercontent.com/payne911/Arise_Deeper/master/core/assets/unrelated/early_demo7.gif)


# Importing the project
#### Firstly
Use the following command in your terminal, from within the location where you want the project to be:
> `git clone https://github.com/payne911/Arise_Deeper.git`

Of course, you could also decide to `fork` the repo and send some *Pull Requests* my way.

#### Secondly
In the root folder of the project, add a `local.properties` file with the following content:
```
# Location of the android SDK
sdk.dir=C:/Users/payne/AppData/Local/Android/Sdk
```

The `sdk.dir=` line should point to your Android SDK folder.

#### Thirdly
In IntelliJ do `File > Open` and select the **root `build.gradle`** as the file to open. An automatic Gradle build should launch.

You should now be able to develop.

#### Optional
You could download the `LibGDX` plugin through IntelliJ's `File > Settings > Plugins`.

### To launch and test
Find the `DesktopLauncher` class and use its `main` method as the `Run Configuration`.