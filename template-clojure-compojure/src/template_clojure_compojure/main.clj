(ns template-clojure-compojure.main
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response status]]
            [template-clojure-compojure.hello :as hello])
  (:gen-class))

;; Create single shared Hello instance
(def hello-instance (hello/create-hello))

(defroutes app-routes
  (GET "/hello" []
    (response (hello/name-as-json hello-instance)))
  
  (POST "/hello" {{name :name} :params}
    (if (and name (not (empty? name)))
      (do
        (hello/set-name hello-instance name)
        (-> (response "")
            (status 201)))
      (-> (response "")
          (status 400))))
  
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      handler/site
      wrap-json-response))

(defn -main
  "Start the HTTP server"
  [& args]
  (println "Starting server on port 3000...")
  (run-jetty app {:port 3000 :join? false}))
