(ns flexiana.flexiana-front.core
    (:require-macros
     [cljs.core.async.macros :refer [go]])
    (:require
      [reagent.core :as r]
      [cljs-http.client :as http]
      [cljs.core.async :refer [<! alt! timeout]]))


(defn report-result
  [state result]
  (swap! state assoc :result result))

(defn scramble
  [state]
  (let [{:keys [str1 str2 result] :as data} @state
        response-chan (http/post "http://localhost:3000/scramble"
                                 {:form-params {:str1 str1 :str2 str2}})
        timeout-chan (timeout 3000)]
    (go
      (alt!
        timeout-chan ([] (report-result state "The server takes too long to answer. Aborting."))
        response-chan ([result]
                       (if (true? (:success result))
                         (report-result state (:body result))
                         (report-result state "There was an error when contacting the server")))))))


(defn on-update-strs
  [state str-key str-val]
  (swap! state assoc str-key str-val)
  (swap! state assoc :result ""))

(defn strs-component
  [state]
  (let [{:keys [str1 str2 result] :as data} @state]
    [:div
     [:p "Enter strings (watch-out for case and special characters)"]
     "String 1: "
     [:input {:type "text"
              :value str1
              :on-change #(on-update-strs state :str1 (-> % .-target .-value))}]
     [:br]
     "String 2: "
     [:input {:type "text"
              :value str2
              :on-change #(on-update-strs state :str2 (-> % .-target .-value))}]
     [:br]
     "Result: " result
     [:input {:type "button"
              :value "Scramble?"
              :on-click #(scramble state)}]]))


(defn home-page []
  [:div [:h2 "Welcome to Reagent"]]
  (let [state (r/atom {:str1 ""
                       :str2 ""
                       :result ""})]
    (fn []
      [strs-component state])))



(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
