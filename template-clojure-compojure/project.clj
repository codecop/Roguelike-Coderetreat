(defproject template-clojure-compojure "0.1.0"
  :description "Roguelike Template"

  :dependencies [[org.clojure/clojure "1.12.1"]
                 [compojure "1.7.2"]
                 [ring/ring-core "1.13.1"] ;; last version for Java 11
                 [ring/ring-jetty-adapter "1.13.1"]
                 [ring/ring-json "0.5.1"]
                 ;;
                 ]

  :plugins [[com.github.clj-kondo/lein-clj-kondo "2025.07.28"] ;; linting
            ;;
            ]

  :main ^:skip-aot template-clojure-compojure.main

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[ring/ring-mock "0.6.2"]
                                  [org.clojure/data.json "2.5.1"]
                                  ;;
                                  ]}})
