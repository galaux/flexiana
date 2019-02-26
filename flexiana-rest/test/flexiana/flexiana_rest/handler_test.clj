(ns flexiana.flexiana-rest.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [flexiana.flexiana-rest.handler :refer :all]))

(deftest test-app

  (testing "not-found route"
    (let [response (app (mock/request :post "/"))]
      (is (= (:status response) 404)))
    (let [response (app (mock/request :post "/invalid"))]
      (is (= (:status response) 404))))

  (testing "scramble route"
    (let [response (app (mock/request :post "/scramble?sthing-else=3"))]
      (is (= (:status response) 400)))
    (with-redefs-fn
      {#'flexiana.flexiana-business/scramble?
       (fn [s1 s2] true)}
      #(let [response (app (mock/request :post "/scramble?str1=rekqodlw&str2=world"))]
         (is (= (:status response) 200))
         (is (= (:body response) "true"))))
    (with-redefs-fn
      {#'flexiana.flexiana-business/scramble?
       (fn [s1 s2] false)}
      #(let [response (app (mock/request :post "/scramble?str1=rekqodlw&str2=world"))]
         (is (= (:status response) 200))
         (is (= (:body response) "false"))))))
