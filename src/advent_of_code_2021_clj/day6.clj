(ns advent-of-code-2021-clj.day6
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn get-safe [m key]
  (let [v (get m key)]
    (if (nil? v)
      0
      v)))

(defn pass-day[fish _]
  (let [fishes (assoc {} 0 (get-safe fish 1)
                         1 (get-safe fish 2)
                         2 (get-safe fish 3)
                         3 (get-safe fish 4)
                         4 (get-safe fish 5)
                         5 (get-safe fish 6)
                         6 (+ (get-safe fish 0) (get-safe fish 7))
                         7 (get-safe fish 8)
                         8 (get-safe fish 0))]
    fishes))

(defn solve [input-filename days-to-pass]
  (let [input (slurp input-filename)
        values (map edn/read-string (str/split input #","))
        initial-fish (frequencies values)
        final-fish (reduce pass-day initial-fish (range days-to-pass))
        fish-count (reduce + (vals final-fish))]
    fish-count))

(solve "day6.txt" 256)
