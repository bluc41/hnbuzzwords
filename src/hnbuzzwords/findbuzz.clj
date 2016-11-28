(ns hnbuzzwords.findbuzz
    (:require [hnbuzzwords.scraper :as scraper])
    (:require [clojure.string :as str]))

(def posts (atom {}));dict mapping words to their "weights" determined by points


(defn print-headlines-and-points []
    (doseq [line (map (fn [[h s]] (str h s)); doseq allows printing in between iterations
                    (partition 2 (scraper/hn-headlines-and-points)))]
                    ;partition splits (1 2 3 4 5 6) into (1 2) (3 4) (5 6)
        (println line)))

(defn processTuple [tuple]
    (let [points (Integer/parseInt ((str/split ((vec tuple) 1) #" ") 0))]
        (doseq [word (str/split ((vec tuple) 0) #" ")]
           (if (contains? @posts (keyword word))
               (swap! posts update-in [(keyword word)] + points)
               (swap! posts assoc (keyword word) points)))))


(defn testing []
    (doseq [page (scraper/hn-all-headlines)]
        (doseq [headline page]
            (println headline))))

(defn addtoDict [headline weight]
    (doseq [word (str/split headline #" ")]
        (if (contains? @posts (keyword word))
            (swap! posts update-in [(keyword word)] + weight); bug
            (swap! posts assoc (keyword word) weight))))

;not very functional -- clean this up
(defn processlines []
    (let [weight (atom 500)]
        (doseq [page (scraper/hn-all-headlines)]
            (doseq [headline page]
                (addtoDict headline weight)
                (swap! weight dec)))))
