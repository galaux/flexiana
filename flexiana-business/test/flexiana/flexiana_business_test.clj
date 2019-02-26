(ns flexiana.flexiana-business-test
  (:require [clojure.test :refer :all]
            [flexiana.flexiana-business :refer :all]))


(deftest test-char-count
  (is (= {\w 1 \o 1 \r 1 \l 1 \d 1}
         (char-count "world")))
  (let [alphabet-once (->> (range 97 123)
                           (map char)
                           (apply str))
        result (char-count alphabet-once)]
    (is (= alphabet-once
           (->> (map first result)
                (apply str))))
    (is (every? #(= 1 %)
                (map second result)))))

(deftest test-scrambled?
  (is (true? (scramble? "rekqodlw" "world")))
  (is (true? (scramble? "cedewaraaossoqqyt" "codewars")))
  (is (false? (scramble? "katas" "steak")))
  (is (true? (scramble? "" "")))
  (is (true? (scramble? nil nil)))
  (is (true? (scramble? "abcd" "abcd"))))
