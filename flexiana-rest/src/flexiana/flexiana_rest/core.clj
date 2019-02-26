(ns flexiana.flexiana-rest.core
  (:require
   [ring.adapter.jetty :refer [run-jetty]]
   [flexiana.flexiana-rest.handler :refer [app]])
  (:gen-class))

(defn -main
  [& args]
  (run-jetty app {:port (Integer/valueOf (or (System/getenv "BACKEND_PORT") "3000"))}))
    
