# Church of Sweden: Open Church

This is a hands-on guide to using the public APIs of the Church of Sweden that have been published as part of the project Open Church.

The guide shows how to build an Android app that displays summer churches near your current location, using the "places" API.

## API Key

The first thing you need to do to get access to any of the APIs of the Church of Sweden is to register with the API portal and generate an API key. This process is completely free; the only information you need to provide is a valid email address.

1. Go to <https://api.svenskakyrkan.se/> and register a user.
2. After you have been logged in to the API portal, go to <https://api.svenskakyrkan.se/a/nycklar> and create a new API key, giving it a name describing what you intend to use it for. You can register several keys.
3. When you now go to <https://api.svenskakyrkan.se/a/nycklar>, you see a list of your API keys. Each key is in the form of a universally unique identifier, e.g., `123e4567-e89b-12d3-a456-426655440000`. It is this UUID you will use in your calls to the church's APIs.

