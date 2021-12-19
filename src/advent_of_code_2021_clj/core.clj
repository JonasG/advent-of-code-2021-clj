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

(defn read-input-file [filename] (slurp filename))

(defn read-boards [board-str]
  (let [board-row-candidates (drop 2 (str/split board-str #"\n"))
        board-rows (filter #(not (empty? %)) board-row-candidates)
        board-row-strings (partition 5 board-rows)
        board-strings (map #(str/join "\n" %) board-row-strings)
        boards (map #(read-single-board %) board-strings)]
    (vec boards)))


(defn parse-input [input-str] 
  (let [boards (read-boards input-str)
        lines (str/split input-str #"\n")
        numbers (read-numbers (first (take 1 lines)))]
    {:numbers numbers :boards boards}))

(defn -main
  [& args]
  (println (parse-input (read-input-file "day4.txt"))))
