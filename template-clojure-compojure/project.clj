(defproject template-clojure-compojure "0.1.0"
  :description "Roguelike Template - Clojure with Compojure"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [compojure "1.6.2"]
                 [ring/ring-core "1.9.5"]
                 [ring/ring-jetty-adapter "1.9.5"]
                 [ring/ring-json "0.5.1"]]
  :main ^:skip-aot template-clojure-compojure.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[ring/ring-mock "0.4.0"]
                                  [org.clojure/data.json "2.4.0"]]}})
