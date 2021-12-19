(ns advent-of-code-2021-clj.core
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn string-split-and-parse [str pattern]
  (let [trimmed (clojure.string/trim str)
        strings (str/split trimmed pattern)
        ints (map edn/read-string strings)]
    (vec ints)))

(defn read-single-board [board-str]
  (let [lines (str/split board-str #"\n")
        numbers (map #(string-split-and-parse % #" +") lines)]
    (vec numbers)))

(defn read-numbers [number-str]
  (let [number-strings (str/split number-str #",")]
    (vec (map edn/read-string number-strings))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
