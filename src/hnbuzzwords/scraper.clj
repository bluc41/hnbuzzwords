(ns hnbuzzwords.scraper
    (:require [net.cgrand.enlive-html :as html]))


(def ^:dynamic *hn-url* "https://news.ycombinator.com"); *-* is convention for dynamic var, used w/ explicit ^:dynamic declaration

(defn fetch-url [url]
    (html/html-resource (java.net.URL. url)));Note trailing dot on java.net.URL.
    ;the trailing dot is shorthand for (new java.net.URL url)

(defn hn-headlines [url]
    (map html/text (html/select (fetch-url url)
                    [:.storylink])))

(defn hn-points []
    (map html/text (html/select (fetch-url *hn-url*)
                    [:.score])))

(defn list-of-URLS []
     (map #(str *hn-url* "/news?p=" %) (range 1 17)))

(defn hn-all-headlines []
    (flatten (map #(hn-headlines %) (list-of-URLS))))

(defn hn-headlines-and-points []
    "maps nodes selected using quasi css selectors to string"
    (map html/text (html/select (fetch-url *hn-url*)
                    #{[:.storylink] [:span.score]})))

;BUG: NEW POSTS DONT SHOW POINTS
