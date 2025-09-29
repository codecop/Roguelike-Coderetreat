(ns template-clojure-compojure.hello-test
  (:require [clojure.test :refer [deftest is testing]]
            [template-clojure-compojure.hello :as hello]))

(deftest get-name-test
  (testing "get default name"
    (let [hello-instance (hello/create-hello)]
      (is (= "World!" (hello/get-name hello-instance))))))
