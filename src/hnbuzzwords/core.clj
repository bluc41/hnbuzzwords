(ns hnbuzzwords.core
  (:gen-class)
  (:require [hnbuzzwords.scraper :as scraper]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (scraper/print-headlines-and-points))
