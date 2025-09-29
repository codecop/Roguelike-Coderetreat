(ns template-clojure-compojure.main
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.handler :as handler]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response status]]
            [template-clojure-compojure.hello :as hello])
  (:gen-class))

;; Single shared Hello instance
(def hello-instance (hello/create-hello))

(defroutes app-routes
  (GET "/hello" []
    (response {:name (hello/get-name hello-instance)}))

  (POST "/hello" {{name :name} :params}
    (if (and name (seq name))
      (do
        (hello/set-name hello-instance name)
        (status 201))
      (status 400))))

(def app
  (-> app-routes
      handler/site
      wrap-json-response))

(defn -main
  "Start the HTTP server"
  [& args]
  (let [port 3000]
    (println (str "Hello started on " port ",\nOpen http://localhost:" port "/hello"))
    (run-jetty app {:port port :join? false})))
