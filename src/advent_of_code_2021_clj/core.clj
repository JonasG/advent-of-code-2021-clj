(ns advent-of-code-2021-clj.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn string-to-int [str]
  (Integer/parseInt str))

(defn string-split-and-parse [str pattern]
  (let [trimmed (clojure.string/trim str)
        strings (str/split trimmed pattern)
        ints (map string-to-int strings)]
    (vec ints)))

(defn read-single-board [board-str]
  (let [lines (str/split board-str #"\n")
        numbers (map #(string-split-and-parse % #" +") lines)]
    (vec numbers)))

(read-single-board "1 2\n3 4")

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
