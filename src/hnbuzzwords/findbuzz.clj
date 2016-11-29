(ns hnbuzzwords.findbuzz
    (:require [hnbuzzwords.scraper :as scraper])
    (:require [clojure.string :as str]))

(def posts (atom {}));dict mapping words in headlines to their "weights" determined by traffic
(def urls (atom {}));dict mapping urls to their "weights" determined by traffic

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

(defn promptKey []
    (println "Enter a word:")
    (let [key (read-line)]
        (println (@posts (keyword key)))))

(defn addHeadlinetoDict [headline weight]
    "Adds all words in a headline to map posts"
    (doseq [word (str/split headline #" ")]
        (if (contains? @posts (keyword word))
            (swap! posts update-in [(keyword word)] + weight)
            (swap! posts assoc (keyword word) weight))))

(defn addURLtoDict [url weight]
    "Adds the URL to map urls"
    (if (contains? @urls (keyword url))
        (swap! urls update-in [(keyword url)] + weight)
        (swap! urls assoc (keyword url) weight)))

;not very functional -- clean this up
(defn processHeadlines []
    (let [weight (atom 600)]
        (doseq [headline (scraper/hn-all-headlines)]
            (addHeadlinetoDict headline @weight)
            (swap! weight dec))))

(defn processURLs []
    (let [weight (atom 600)]
        (doseq [url (scraper/hn-all-urls)]
            (addURLtoDict url @weight)
            (swap! weight dec))))

(defn testing []
    (doseq [entry (sort-by last @urls)]
        (println entry)))

(defn printSorted [atom]
    (doseq [entry (sort-by last @atom)]
        (println entry)))
