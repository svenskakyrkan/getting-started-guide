# Church of Sweden: Open Church

This is a hands-on guide to using the public APIs of the Church of Sweden that have been published as part of the project Open Church.

The guide shows how to build an Android app that displays summer churches near your current location, using the "places" API.

## Generating an API Key

The first thing you need to do to get access to any of the APIs of the Church of Sweden is to register with the API portal and generate an API key. This process is completely free; the only information you need to provide is a valid email address.

1. Go to <https://api.svenskakyrkan.se/> and register a user.
2. After you have been logged in to the API portal, go to <https://api.svenskakyrkan.se/a/nycklar> and create a new API key, giving it a name describing what you intend to use it for. You can register several keys.
3. When you now go to <https://api.svenskakyrkan.se/a/nycklar>, you see a list of your API keys. Each key is in the form of a universally unique identifier, e.g., `123e4567-e89b-12d3-a456-426655440000`. It is this UUID you will use in your calls to the church's APIs.

## Building the demo app

To build the Summer Churches demo app, you must have the Android SDK installed. You need at least Android 3.0 Honeycomb (API level 11). The same goes for any device you want to use for testing.

You probably also want to install Android Studio since it makes development of Android apps easier.

You can download both the Android SDK and Android Studio here:
<http://developer.android.com/sdk/index.html>

You also need to have Gradle installed:
<https://gradle.org/downloads>

Make sure the environment variable variable `ANDROID_HOME` is set to the correct value.

    # An example for Linux:
    export ANDROID_HOME=/opt/android-sdk

    rem An example for Windows:
    set ANDROID_HOME=C:\tools\Android\sdk

You now need to replace the default API key with the key you created in an earlier step. Locate the file 

`summerchurch/core/src/main/java/se/svenskakyrkan/android/core/place/HttpUrlConnectionPlaceFinder.java`

and replace the text `REPLACE_ME!` with your own API key.

Now build the app using Gradle from a command prompt:

    cd summerchurch
    gradle build

The build should end with the text `BUILD SUCCESSFUL`.

If you use Android Studio, you can import the project using `Import Non-Android Studio project`.

## Running the demo app

If you have successfully build the app, you can now test it on a real device. First, connect your device to your computer using a USB cable. Then give the command

    gradle installDebug

This will install the app on the connected device, but it will not start it. Do this manually by locating the `Summer Churches` app and run it, preferably somewhere where you have a GPS signal. If you are in within a reasonable distance, currently 400 km or around 250 miles, from any summer church, the app will display them on a map.

## Church of Sweden Public APIs

To locate summer churches in your vicinity, the app uses the Places API, one of the public APIs provided by the Church of Sweden:
<http://api.svenskakyrkan.se/platser/v3-latest/doc/>

Other public APIs can be found in the API portal:
<https://api.svenskakyrkan.se/tjanster>

The Open Church project has also resulted in a description of these APIs using [DCAT-AP](https://joinup.ec.europa.eu/asset/dcat_application_profile/description), which makes them searchable from <http://oppnadata.se/>.
