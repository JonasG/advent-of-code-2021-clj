(ns advent-of-code-2021-clj.day8
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(def segment-to-number-mapping {2 1
                                4 4
                                3 2
                                7 8})

(defn solve [input-filename]
  (let [lines (str/split (slurp input-filename) #"\n")
        outputs (flatten (map #(take 4 (drop 11 (str/split % #" "))) lines))
        output-length (map count outputs)
        identifiable-numbers (filter #(contains? segment-to-number-mapping %) output-length)]
    (count identifiable-numbers)))

(solve "day8.txt")
