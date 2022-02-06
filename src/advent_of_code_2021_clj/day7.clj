(ns advent-of-code-2021-clj.day7
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn abs [num] (max num (- num)))

(defn calculate-fuel-cost [cost-fn positions target-position]
  (let [distances (map #(abs (- target-position %)) positions)
        costs (map cost-fn distances)]
    (reduce + costs)))

(defn solve [input-filename fuel-cost-fn]
  (let [positions (map edn/read-string (str/split (slurp input-filename) #","))
        max-value (apply max positions)
        fuel-costs (map #(fuel-cost-fn positions %) (range (inc max-value)))]
    (apply min fuel-costs)))

(defn distance-based-fuel-cost-unmem [distance] (reduce + (range (inc distance))))
(def distance-based-fuel-cost (memoize distance-based-fuel-cost-unmem))

;; (solve "day7.txt" (partial calculate-fuel-cost (fn [x] x))) ;; 352997
(solve "day7.txt" (partial calculate-fuel-cost distance-based-fuel-cost)) ;; 101571302
