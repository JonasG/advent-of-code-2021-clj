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
                      (remove (get m 9))
                      (first)))
      (assoc m 6 (->> as-sets
                      (filter #(= 6 (count %)))
                      (remove (get m 0))
                      (remove (get m 9))
                      (first)))
      (assoc m 5 (->> as-sets
                      (filter #(= 5 (count %)))
                      (filter #(set/subset? % (get m 6)))
                      (first)))
      (assoc m 2 (->> as-sets
                      (filter #(= 5 (count %)))
                      (remove (get m 5))
                      (remove (get m 3)))))))

(number-to-segment-mapping '("acedgfb" "cdfbe" "gcdfa" "fbcad" "dab" "cefabd" "cdfgeb" "eafb" "cagedb" "ab"))

(defn solve [input-filename]
  (let [lines (str/split (slurp input-filename) #"\n")
        outputs (flatten (map #(take 4 (drop 11 (str/split % #" "))) lines))
        output-length (map count outputs)
        identifiable-numbers (filter #(contains? segment-to-number-mapping %) output-length)]
    (count identifiable-numbers)))

;; (solve "day8.txt")
