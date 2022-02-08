(ns advent-of-code-2021-clj.day8
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]
            [clojure.set    :as set])
  (:gen-class))

(defn number-to-segment-mapping [input]
  (let [as-sets (map set input)]
    (as->     {1 (first (segment-count 2 as-sets))
               4 (first (segment-count 4 as-sets))
               7 (first (segment-count 3 as-sets))
               8 (first (segment-count 7 as-sets))} m
      (assoc m 3 (->> as-sets
                      (filter #(= 5 (count %)))
                      (filter #(set/superset? % (get m 1)))
                      (first)))
      (assoc m 9 (->> as-sets
                      (filter #(= 6 (count %)))
                      (filter #(set/superset? % (get m 3)))
                      (first)))
      (assoc m 0 (->> as-sets
                      (filter #(= 6 (count %)))
                      (filter #(set/superset? % (get m 1)))
                      (remove #(= % (get m 9)))
                      (first)))
      (assoc m 6 (->> as-sets
                      (filter #(= 6 (count %)))
                      (remove #(= % (get m 0)))
                      (remove #(= % (get m 9)))
                      (first)))
      (assoc m 5 (->> as-sets
                      (filter #(= 5 (count %)))
                      (filter #(set/subset? % (get m 6)))
                      (first)))
      (assoc m 2 (->> as-sets
                      (filter #(= 5 (count %)))
                      (remove #(= % (get m 3)))
                      (remove #(= % (get m 5)))
                      (first))))))

(defn decode [signal-patterns input]
  (let [to-number-mapping (set/map-invert (number-to-segment-mapping signal-patterns))]
    (get to-number-mapping (set input))))

(defn decode-entry [entry]
  (map #(decode (take 10 entry) %) (take 4 (drop 10 entry))))

(defn solve [input-filename]
  (let [lines (str/split (slurp input-filename) #"\n")
        signal-patterns (map #(as-> % data
                                (str/split data #" ")
                                (take 10 data)) lines)
        outputs (map #(as-> % data
                        (str/split data #" ")
                        (drop 11 data)
                        (take 4 data)) lines)
        entries (map (comp flatten vector) signal-patterns outputs)
        output-as-lists (map decode-entry entries)
        output-as-numbers (map #(+ (* 1000 (first %)) (* 100 (second %)) (* 10 (nth % 2)) (last %)) output-as-lists)]
    (reduce + output-as-numbers)))

(solve "day8.txt")
