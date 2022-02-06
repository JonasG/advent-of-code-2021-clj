(ns advent-of-code-2021-clj.day7
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn abs [num] (max num (- num)))

(defn calculate-fuel-cost [positions target-position]
  (let [fuel-cost-per-position (map #(abs (- target-position %)) positions)]
    (reduce + fuel-cost-per-position)))

(defn solve [input-filename]
  (let [positions (map edn/read-string (str/split (slurp input-filename) #","))
        max-value (apply max positions)
        fuel-costs (map #(calculate-fuel-cost positions %) (range (inc max-value)))]
    (apply min fuel-costs)))

(solve "day7.txt")
