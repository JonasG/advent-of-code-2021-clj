(ns advent-of-code-2021-clj.day5
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn generate-coordinates [[x1 y1 x2 y2]]
  (let [min-x (min x1 x2)
        min-y (min y1 y2)
        max-x (max x1 x2)
        max-y (max y1 y2)
        x-range (range min-x (inc max-x))
        y-range (range min-y (inc max-y))
        max-range-count (max (count x-range) (count y-range))]
    (if (< (count x-range) (count y-range))
      (map vector (repeat max-range-count (first x-range)) y-range)
      (map vector x-range (repeat max-range-count (first y-range))))))

(defn horizontal-or-veritcal? [[x1 y1 x2 y2]]
  (or (= x1 x2)
      (= y1 y2)))

(defn create-coordinate-usages [state coordinate]
  (update state coordinate #(if (nil? %) 1 (inc %))))

(defn day5part1 [input-filename]
  (let [input (slurp input-filename)
        lines (str/split input #"\n")
        parsed-numbers-as-strings (mapv #(vector (re-seq #"\d+" %)) lines)
        flatted (mapv flatten parsed-numbers-as-strings)
        integers (mapv #(mapv edn/read-string %) flatted)
        horizontal-and-vertical-lines (filter horizontal-or-veritcal? integers)
        coordinates-nested (map #(generate-coordinates %) horizontal-and-vertical-lines)
        coordinates (partition 2 (flatten coordinates-nested))
        coordinate-usages (reduce create-coordinate-usages {} coordinates)
        coordinate-overlaps (vals coordinate-usages)
        overlap-count (count (filter #(< 1 %) coordinate-overlaps))]
    overlap-count))

;; (day5part1 "day5-example.txt")
(day5part1 "day5.txt")

;; 0,9 -> 5,9 H
;; 8,0 -> 0,8
;; 9,4 -> 3,4 H
;; 2,2 -> 2,1 H
;; 7,0 -> 7,4 H
;; 6,4 -> 2,0
;; 0,9 -> 2,9 H
;; 3,4 -> 1,4 H
;; 0,0 -> 8,8
;; 5,5 -> 8,2
