(ns hnbuzzwords.scraper
    (:require [net.cgrand.enlive-html :as html]))


(def ^:dynamic *hn-url* "https://news.ycombinator.com"); *-* is convention for dynamic var, used w/ explicit ^:dynamic declaration

(defn fetch-url [url]
    (html/html-resource (java.net.URL. url)));Note trailing dot on java.net.URL.
    ;the trailing dot is shorthand for (new java.net.URL url)

(defn hn-headlines-and-points []
    "maps nodes selected using quasi css selectors to string"
    (map html/text (html/select (fetch-url *hn-url*)
                    #{[:td.title :a] [:td.subtext html/first-child]})))

;TODO: get titles/points from multiple(50) pages

(defn print-headlines-and-points []
    (doseq [line (map (fn [[h s]] (str h " (" s ")")); doseq allows printing in between iterations
                    (partition 2 (hn-headlines-and-points)))]
                    ;partition splits (1 2 3 4 5 6) into (1 2) (3 4) (5 6)
        (println line)))
