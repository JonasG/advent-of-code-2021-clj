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
(defn board-won? [board]
  (if (or (some-row-marked? board) (some-column-marked? board))
    board
    false))

(defn iterate-2d-vector-filtered [board filter]
  (for [[_ row] (map-indexed list board)
        [_ value] (map-indexed list row)
        :when (filter value)]
    value))

(defn sum [coll] (reduce + coll))
(defn score [board winning-number]
  (* winning-number (sum (iterate-2d-vector-filtered board #(not (is-marked? %))))))

(defn mark-board [board number-drawn]
  (mapv #(replace {number-drawn "X"} %) board))

(defn play-bingo [boards draws]
  (reduce (fn [board-state number-drawn]
            (let [new-board-state (mapv #(mark-board % number-drawn) board-state)]
              (if (some board-won? new-board-state)
                (reduced {:winning-board-state (some board-won? new-board-state) :number number-drawn})
                new-board-state)))
          boards
          draws))

(defn day4-part1 [input]
  (let [result (play-bingo (:boards input) (:numbers input))]
     (score (:winning-board-state result) (:number result))))

(defn parse-input [input-str] 
  (let [boards (read-boards input-str)
        lines (str/split input-str #"\n")
        numbers (read-numbers (first (take 1 lines)))]
    {:numbers numbers :boards boards}))

(day4-part1 (parse-input (read-input-file "day4.txt"))) ;; 67716

(defn -main
  [& args]
  (println (parse-input (read-input-file "day4.txt"))))
