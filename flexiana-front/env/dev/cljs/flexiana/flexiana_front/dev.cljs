(ns ^:figwheel-no-load flexiana.flexiana-front.dev
  (:require
    [flexiana.flexiana-front.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
