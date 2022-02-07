(ns advent-of-code-2021-clj.day8
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn segment-count [target input] (filter #(= (count %) target) input))

(def example-input '("be" "cfbegad" "cbdgef" "fgaecd" "cgeb" "fdcge" "agebfd" "fecdb" "fabcd" "edb"))

(defn find-supersets-of [target as-sets]
  (filter #(clojure.set/superset? % target) as-sets))

(defn number-to-segment-mapping [input]
  (let [as-sets (map set input)]
    (as->     {1 (first (segment-count 2 as-sets))
               4 (first (segment-count 4 as-sets))
               7 (first (segment-count 3 as-sets))
               8 (first (segment-count 7 as-sets))} m
      (assoc m 3 (first (find-supersets-of (get m 1) (segment-count 5 as-sets))))
      (assoc m 9 (first (find-supersets-of (get m 3) (segment-count 6 as-sets))))
      (assoc m 0 (first (->> as-sets
                             (segment-count 6)
                             (find-supersets-of (get m 1))
                             (remove (get m 9)))))
      (assoc m 6 (first (remove (get m 9) (remove (get m 0) (segment-count 6 as-sets)))))
      (assoc m 5 (first (filter #(clojure.set/subset? % (get m 6)) (segment-count 5 as-sets))))
      (assoc m 2 (first (remove (get m 3) (remove (get m 5) (segment-count 5 as-sets))))))))

(number-to-segment-mapping '("acedgfb" "cdfbe" "gcdfa" "fbcad" "dab" "cefabd" "cdfgeb" "eafb" "cagedb" "ab"))

(defn solve [input-filename]
  (let [lines (str/split (slurp input-filename) #"\n")
        outputs (flatten (map #(take 4 (drop 11 (str/split % #" "))) lines))
        output-length (map count outputs)
        identifiable-numbers (filter #(contains? segment-to-number-mapping %) output-length)]
    (count identifiable-numbers)))

;; (solve "day8.txt")
