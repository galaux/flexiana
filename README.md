Flexiana project
================

A simple [ClojureScript](https://clojurescript.org/) + REST API application.

This project has 3 components:

- `flexiana-business`:
  - the core _business_ functionality of the app
- `flexiana-rest`:
  - a REST API to expose `flexiana-business`
- `flexiana-front`:
  - a ClojureScript project that calls an instance of `flexiana-rest`
  - when compiled, provides a JS application to run on a webserver

Modern browsers prevent application initiating from an URI to call another URI
(see [CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing)).
In order to run this stack, the REST part must know the front's URI. When
in production, if `flexiana-rest` and `flexiana-front` are served from different
URI, one must thus specify the correct `FRONTEND_URI` to `flexiana-rest`.
Nothing needs to be done when run in development otherwise.


Development
-----------

1. Install `flexiana-business`:
   ```
   cd flexiana-business
   lein install
   ```
2. Run `flexiana-rest`
   ```
   cd flexiana-rest
   # You can use FRONTEND_URI="http://127.0.0.1:3000" to set the front URI
   lein ring server
   # You can test it with:
   curl --data 'str1=dorwl&str2=world'  http://localhost:3000/scramble
   # returns true
   curl --data 'str1=dorwl&str2=worlde'  http://localhost:3000/scramble
   # returns false
   ```
3. In another term, run `flexiana-front`:
   ```
   cd flexiana-front
   lein figwheel
   # Figwheel will run the application and open a browser when ready
   # otherwise open http://0.0.0.0:3449
   ```


Production
----------

1. Install `flexiana-business`:
   ```
   cd flexiana-business
   lein install
   ```
2. Build and run `flexiana-rest`
   ```
   cd flexiana-rest
   lein uberjar
   # System environment variables optional
   FRONTEND_URI="http://localhost:3449" \
     BACKEND_PORT=3000 \
     java -jar target/flexiana-rest-0.1.0-SNAPSHOT-standalone.jar
   ```
3. Build and deploy `flexiana-front`
   ```
   cd flexiana-front
   lein package
   # Deploy files in ./public
   ```
