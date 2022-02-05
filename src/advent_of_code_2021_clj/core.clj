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

(defn all-marked? [coll] (every? #(= "X" %) coll))
(defn won-by-row? [board] (some all-marked? board))

(won-by-row? [[ 1  2  3  4  5]
              [ 6  7  8  9 10]
              [11 12 13 14 15]
              [16 17 18 19 20]
              [21 22 23 24 25]])
(won-by-row? [[  1   2   3   4   5]
              ["X" "X" "X" "X" "X"]
              [ 11  12  13  14  15]
              [ 16  17  18  19  20]
              [ 21  22  23  24  25]])

(def example-data [[ 1  2  3  4  5]
                 [ 6  7  8  9 "X"]
                 [11 12 13 14 "X"]
                 [16 17 18 19 "X"]
                 [21 22 23 24 "X"]])

(defn won-by-column? [board]
  (or (all-marked? [(get-in board [0 0])
                    (get-in board [1 0])
                    (get-in board [2 0])
                    (get-in board [3 0])
                    (get-in board [4 0])])
      (all-marked? [(get-in board [0 1])
                    (get-in board [1 1])
                    (get-in board [2 1])
                    (get-in board [3 1])
                    (get-in board [4 1])])
      (all-marked? [(get-in board [0 2])
                    (get-in board [1 2])
                    (get-in board [2 2])
                    (get-in board [3 2])
                    (get-in board [4 2])])
      (all-marked? [(get-in board [0 3])
                    (get-in board [1 3])
                    (get-in board [2 3])
                    (get-in board [3 3])
                    (get-in board [4 3])])
      (all-marked? [(get-in board [0 4])
                    (get-in board [1 4])
                    (get-in board [2 4])
                    (get-in board [3 4])
                    (get-in board [4 4])])))

(won-by-column? [[ 1  2  3  4  5]
                 [ 6  7  8  9 10]
                 [11 12 13 14 15]
                 [16 17 18 19 20]
                 [21 22 23 24 25]])

(won-by-column? [[ "X"  2  3  4  5]
                 [ "X"  7  8  9 10]
                 ["X" 12 13 14 15]
                 ["X" 17 18 19 20]
                 ["X" 22 23 24 25]])

(won-by-column? [[ 1  "X"  3  4  5]
                 [ 6  "X"  8  9 10]
                 [11 "X" 13 14 15]
                 [16 "X" 18 19 20]
                 [21 "X" 23 24 25]])

(won-by-column? [[ 1  2  "X"  4  5]
                 [ 6  7  "X"  9 10]
                 [11 12 "X" 14 15]
                 [16 17 "X" 19 20]
                 [21 22 "X" 24 25]])

(won-by-column? [[ 1  2  3  "X"  5]
                 [ 6  7  8  "X" 10]
                 [11 12 13 "X" 15]
                 [16 17 18 "X" 20]
                 [21 22 23 "X" 25]])

(won-by-column? [[ 1  2  3  4 "X"]
                 [ 6  7  8  9 "X"]
                 [11 12 13 14 "X"]
                 [16 17 18 19 "X"]
                 [21 22 23 24 "X"]])

(defn parse-input [input-str] 
  (let [boards (read-boards input-str)
        lines (str/split input-str #"\n")
        numbers (read-numbers (first (take 1 lines)))]
    {:numbers numbers :boards boards}))

(defn -main
  [& args]
  (println (parse-input (read-input-file "day4.txt"))))
