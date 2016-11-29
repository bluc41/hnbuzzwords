(ns hnbuzzwords.core
  (:gen-class)
  (:require [hnbuzzwords.scraper :as scraper])
  (:require [hnbuzzwords.findbuzz :as findbuzz]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "These are the sorted words in headlines")
  (findbuzz/processHeadlines)
  (findbuzz/printSorted findbuzz/posts)
  (println "These are sorted urls")
  (findbuzz/processURLs)
  (findbuzz/printSorted findbuzz/urls))
