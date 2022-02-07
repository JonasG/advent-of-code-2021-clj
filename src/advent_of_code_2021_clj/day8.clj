(ns advent-of-code-2021-clj.day8
  (:require [clojure.string :as str]
            [clojure.edn    :as edn]          )
  (:gen-class))

(defn contains-all-segments-of [target input]
  (filter #(= (set target) (clojure.set/intersection (set target) (set %))) (remove #(= % target) input)))

(defn segment-count [target input] (filter #(= (count %) target) input))

(def example-input '("be" "cfbegad" "cbdgef" "fgaecd" "cgeb" "fdcge" "agebfd" "fecdb" "fabcd" "edb"))

(defn find-supersets-of [target input]
  (let [as-sets (map set (remove #(= target %) input))
        supersets (filter #(clojure.set/superset? % (set target)) as-sets)]
    (map str/join supersets)))

;; (find-supersets-of "fecdb" example-input)

(defn segment-to-number-mapping [input]
  (as->     {1 (first (segment-count 2 input))
             4 (first (segment-count 4 input))
             7 (first (segment-count 3 input))
             8 (first (segment-count 7 input))} m
    (assoc m 3 (first (contains-all-segments-of (get m 1) (segment-count 5 input))))
    (assoc m 9 (first (find-supersets-of (get m 3) (segment-count 6 input))))))

(segment-to-number-mapping '("be" "cfbegad" "cbdgef" "fgaecd" "cgeb" "fdcge" "agebfd" "fecdb" "fabcd" "edb"))

;; 1 -- består av två segment
;; 4 -- består av fyra segment
;; 7 -- består av tre segment
;; 8 -- består av sju segment
;; mittensegmentet => härled genom att ta alla 6-segment-siffor och hitta det segment som är med i alla utom en (nollan saknar detta)
;; 0 -- 8 - mittensegmentet
;; toppsegmentet => 7 - 1
;; 9 -- den 6-segment-siffra som inte är noll och som har två segment gemensamma med
;; 6 -- den återstående 6-segment-siffran
;; 2 -- den 5-segment-siffra som jag får om jag tar bort två segment från 9
;; 3 -- den 5-segment-siffra som delar två segment med 1
;; 5 -- återstående

(defn solve [input-filename]
  (let [lines (str/split (slurp input-filename) #"\n")
        outputs (flatten (map #(take 4 (drop 11 (str/split % #" "))) lines))
        output-length (map count outputs)
        identifiable-numbers (filter #(contains? segment-to-number-mapping %) output-length)]
    (count identifiable-numbers)))

;; (solve "day8.txt")
