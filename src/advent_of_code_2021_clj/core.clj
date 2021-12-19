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
  (let [lines (str/split-lines board-str)
        numbers (map #(string-split-and-parse % #" +") lines)]
    (vec numbers)))

(defn read-numbers [number-str]
  (let [number-strings (str/split number-str #",")]
    (vec (map edn/read-string number-strings))))

(defn read-input-file [filename] (slurp filename))

(defn read-boards [board-str]
  (let [board-row-candidates (drop 2 (str/split board-str #"\n"))
        board-rows (remove empty? board-row-candidates)
        board-row-strings (partition 5 board-rows)
        boards (map #((comp read-single-board (partial str/join "\n")) %) board-row-strings)]
    (vec boards)))

(defn mark-single-board [number board]
  (vec (map #(replace {number "X"} %) board)))

(defn mark-boards [number boards]
  (vec (map (partial mark-single-board number) boards)))

(mark-single-board 8
          [[1  2  3  4  5]
           [6  7  8  9 10]
           [11 12 13 14 15]
           [16 17 18 19 20]
           [21 22 23 24 25]])

(mark-boards 8
         [[[1  2  3  4  5]
           [6  7  8  9 10]
           [11 12 13 14 15]
           [16 17 18 19 20]
           [21 22 23 24 25]]
          [[1  2  3  4  5]
           [6  7  8  9 10]
           [11 12 13 14 15]
           [16 17 18 19 20]
           [21 22 23 24 25]]])

(defn parse-input [input-str] 
  (let [boards (read-boards input-str)
        lines (str/split input-str #"\n")
        numbers (read-numbers (first (take 1 lines)))]
    {:numbers numbers :boards boards}))

(defn -main
  [& args]
  (println (parse-input (read-input-file "day4.txt"))))
