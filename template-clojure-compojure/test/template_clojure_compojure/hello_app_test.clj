(ns template-clojure-compojure.hello-app-test
  (:require [clojure.test :refer [deftest is testing]]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [template-clojure-compojure.main :refer [app hello-instance]]
            [template-clojure-compojure.hello :as hello]))

(defn parse-body [response]
  (json/read-str (:body response) :key-fn keyword))

(defn reset-hello-instance []
  (hello/set-name hello-instance "World!"))

(deftest get-name-test
  (testing "GET /hello returns default name"
    (reset-hello-instance)
    (let [response (app (mock/request :get "/hello"))]
      (is (= 200 (:status response)))
      (is (= "application/json; charset=utf-8" (get-in response [:headers "Content-Type"])))
      (is (= {:name "World!"} (parse-body response))))))

(deftest update-name-test
  (testing "POST /hello updates name and GET returns new name"
    (reset-hello-instance)
    ;; First POST to update name
    (let [response (app (assoc (mock/request :post "/hello")
                               :params {:name "Peter"}))]
      (is (= 201 (:status response))))

    ;; Then GET to verify the update
    (let [response (app (mock/request :get "/hello"))]
      (is (= 200 (:status response)))
      (is (= {:name "Peter"} (parse-body response))))))
