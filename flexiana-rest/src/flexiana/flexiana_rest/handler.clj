(ns flexiana.flexiana-rest.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [flexiana.flexiana-business :refer :all]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response status]]
            [clojure.tools.logging :as log]))


(def ^:const http-status-bad-request 400)

(defn handler
  [str1 str2]
  (if (not (and (string? str1) (string? str2)))
    (-> (response "Missing or bad input")
        (status http-status-bad-request))
    (-> (scramble? str1 str2)
        str
        response)))


(defroutes app-routes
  (POST "/scramble" [str1 str2] (handler str1 str2))
  (route/not-found "Not Found"))


(def ^:const default-allowed-origin "http://localhost:3449")

(def app
  (let [allowed-origin (re-pattern (or (System/getenv "FRONTEND_URI") default-allowed-origin))]
    (log/info "Using frontend URI:" allowed-origin)
    (-> app-routes
        (wrap-defaults api-defaults)
        (wrap-cors :access-control-allow-origin [allowed-origin]
                   :access-control-allow-methods [:post]
                   :access-control-allow-credentials "true")
        wrap-params)))
