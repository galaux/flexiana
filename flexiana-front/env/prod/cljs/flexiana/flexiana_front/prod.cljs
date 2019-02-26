(ns flexiana.flexiana-front.prod
  (:require
    [flexiana.flexiana-front.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
