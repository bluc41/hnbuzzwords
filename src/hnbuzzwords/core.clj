(ns hnbuzzwords.core
  (:gen-class)
  (:require [hnbuzzwords.scraper :as scraper])
  (:require [hnbuzzwords.findbuzz :as findbuzz]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (findbuzz/print-headlines-and-points))
