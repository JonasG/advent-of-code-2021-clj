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

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn is-marked? [value] (= "X" value))
(defn every-marked? [coll] (every? is-marked? coll))
(defn some-row-marked? [board] (some every-marked? board))
(defn some-column-marked? [board] (some-row-marked? (transpose board)))

(some-column-marked? [[ 1  2  3  4  5]
                 [ 6  7  8  9 10]
                 [11 12 13 14 15]
                 [16 17 18 19 20]
                 [21 22 23 24 25]])

(some-column-marked? [[ "X"  2  3  4  5]
                 [ "X"  7  8  9 10]
                 ["X" 12 13 14 15]
                 ["X" 17 18 19 20]
                 ["X" 22 23 24 25]])

(some-column-marked? [[ 1  "X"  3  4  5]
                 [ 6  "X"  8  9 10]
                 [11 "X" 13 14 15]
                 [16 "X" 18 19 20]
                 [21 "X" 23 24 25]])

(some-column-marked? [[ 1  2  "X"  4  5]
                 [ 6  7  "X"  9 10]
                 [11 12 "X" 14 15]
                 [16 17 "X" 19 20]
                 [21 22 "X" 24 25]])

(some-column-marked? [[ 1  2  3  "X"  5]
                 [ 6  7  8  "X" 10]
                 [11 12 13 "X" 15]
                 [16 17 18 "X" 20]
                 [21 22 23 "X" 25]])

(some-column-marked? [[ 1  2  3  4 "X"]
                 [ 6  7  8  9 "X"]
                 [11 12 13 14 "X"]
                 [16 17 18 19 "X"]
                 [21 22 23 24 "X"]])

(some-row-marked? [[ 1  2  3  4  5]
              [ 6  7  8  9 10]
              [11 12 13 14 15]
              [16 17 18 19 20]
              [21 22 23 24 25]])
(some-row-marked? [[  1   2   3   4   5]
              ["X" "X" "X" "X" "X"]
              [ 11  12  13  14  15]
              [ 16  17  18  19  20]
              [ 21  22  23  24  25]])

(def example-data [[ 1  2  3  4  5]
                 [ 6  7  8  9 "X"]
                 [11 12 13 14 "X"]
                 [16 17 18 19 "X"]
                 [21 22 23 24 "X"]])

(defn board-won? [board] (or (some-row-marked? board) (some-column-marked? board)))

(board-won? [[ 1  2  3  4 5]
                 [ 6  7  8  9 10]
                 [11 12 13 14 10]
                 [16 17 18 19 10]
                 ["X" "X" 5 "X" "X"]])
(board-won? [[ 1  2  3  4 "X"]
                 [ 6  7  8  9 "X"]
                 ["X" "X" "X" "X" "X"]
                 [16 17 18 19 "X"]
                 [21 22 23 24 "X"]])

(defn iterate-2d-vector-filtered [board filter]
  (for [[_ row] (map-indexed list board)
        [_ value] (map-indexed list row)
        :when (filter value)]
    value))

(iterate-2d-vector-filtered [[1 2]
                             ["X" 4]] #(not (= "X" %)))

(defn sum [coll] (reduce + coll))

(defn score [board winning-number]
  (* winning-number (sum (iterate-2d-vector-filtered board #(not (is-marked? %))))))

(score [[1 2]
        ["X" 4]] 42)

(defn parse-input [input-str] 
  (let [boards (read-boards input-str)
        lines (str/split input-str #"\n")
        numbers (read-numbers (first (take 1 lines)))]
    {:numbers numbers :boards boards}))

(defn -main
  [& args]
  (println (parse-input (read-input-file "day4.txt"))))
