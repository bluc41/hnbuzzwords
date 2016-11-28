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
    (doseq [keyval @posts]
        (println keyval)))

(defn addtoDict [headline weight]
    (doseq [word (str/split headline #" ")]
        (if (contains? @posts (keyword word))
            (swap! posts update-in [(keyword word)] + weight)
            (swap! posts assoc (keyword word) weight))))


(defn processlines []
    (let [weight (atom 30)]
        (doseq [headline (scraper/hn-headlines)]
            (addtoDict headline @weight)
            (swap! weight dec))))
