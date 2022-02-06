(ns advent-of-code-2021-clj.day5
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn generate-coordinates[[x1 y1 x2 y2]]
  (let [x-range (if (< x1 x2) (range x1 (inc x2)) (range x1 (dec x2) -1))
        y-range (if (< y1 y2) (range y1 (inc y2)) (range y1 (dec y2) -1))]
    (cond
      (= (count x-range) (count y-range)) (map vector x-range y-range)
      (< (count x-range) (count y-range)) (map vector (repeat (count y-range) (first x-range)) y-range)
      :else (map vector x-range (repeat (count x-range) (first y-range))))))

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
(day5part1 "day5.txt") ;; 4745
