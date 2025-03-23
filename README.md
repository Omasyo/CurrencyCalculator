## Running the application

### Requirements

- [Android SDK and related tools](https://developer.android.com/studio)
- [Android Studio](https://developer.android.com/studio) or [IntelliJ Idea](https://www.jetbrains.com/idea/) or any suitable IDE
- Android Emulator and a virtual device or a physical android device

### How to Run

- Clone this repository on you computer device and open the project using Android Studio, IntelliJ Idea or any suitable
  IDE.
- Connect to your Android device or virtual device using [adb](https://developer.android.com/tools/adb).
- Using Android Studio/IDEA you can sync the gradle project and run the application using the default configurations.
- Alternatively you can install the app directly on the device using gradle by running `./gradlew installRelease` from
  the root directory.
- You can modify the API key [here](app/src/main/java/com/omasyo/currencycalculator/network/CurrencyService.kt).

> **Note:**
> Some of the endpoints are not allowed on the Fixer API free tier, so I used dummy data.

### Download
You can download the app [here](https://github.com/Omasyo/CurrencyCalculator/releases/download/1.0.0/app-release.apk)
