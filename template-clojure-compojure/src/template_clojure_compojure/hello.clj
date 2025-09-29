(ns template-clojure-compojure.hello)

(defn create-hello
  "Creates a new Hello instance with default name"
  []
  (atom {:name "World!"}))

(defn get-name
  "Gets the current name from Hello instance"
  [hello]
  (:name @hello))

(defn set-name
  "Sets a new name in Hello instance"
  [hello new-name]
  (swap! hello assoc :name new-name))
