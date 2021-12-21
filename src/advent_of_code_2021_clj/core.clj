(ns advent-of-code-2021-clj.core
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn string-split-and-parse [str pattern]
  (let [trimmed (clojure.string/trim str)
        strings (str/split trimmed pattern)
        ints (mapv edn/read-string strings)]
    ints))

(defn read-single-board [board-str]
  (let [lines (str/split-lines board-str)
        numbers (mapv #(string-split-and-parse % #" +") lines)]
    numbers))

(defn read-numbers [number-str]
  (let [number-strings (str/split number-str #",")]
    (mapv edn/read-string number-strings)))

(defn read-input-file [filename] (slurp filename))

(defn read-boards [board-str]
  (let [board-row-candidates (drop 2 (str/split board-str #"\n"))
        board-rows (remove empty? board-row-candidates)
        board-row-strings (partition 5 board-rows)
        boards (mapv #((comp read-single-board (partial str/join \newline)) %) board-row-strings)]
    boards))

(defn mark-number-on-board [number board]
  (mapv (partial replace {number "X"}) board))

(defn mark-number-on-boards [number boards]
  (mapv (partial mark-number-on-board number) boards))

(defn parse-input [input-str] 
  (let [boards (read-boards input-str)
        lines (str/split input-str #"\n")
        numbers (read-numbers (first (take 1 lines)))]
    {:numbers numbers :boards boards}))

(defn -main
  [& args]
  (println (parse-input (read-input-file "day4.txt"))))
