(ns advent-of-code-2021-clj.core-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021-clj.core :refer :all]))

(deftest test-read-single-board
  (testing "single board is created as expected without needing to trim whitespaces or remove adjacent whitespaces"
  (is (= 
      [[ 0  1  2  3  4]
       [ 5  6  7  8  9]
       [10 11 12 13 14]
       [15 16 17 18 19]
       [20 21 22 23 24]]
      (read-single-board "0 1 2 3 4\n5 6 7 8 9\n10 11 12 13 14\n15 16 17 18 19\n20 21 22 23 24"))))

  (testing "single board is created as expected with whitespaces that need trimming"
  (is (= 
    [[0  1  2  3  4]
     [5  6  7  8  9]
     [10 11 12 13 14]
     [15 16 17 18 19]
     [20 21 22 23 24]]
    (read-single-board " 0 1 2 3 4\n 5 6 7 8 9\n 10 11 12 13 14\n 15 16 17 18 19\n 20 21 22 23 24"))))

  (testing "single board is created as expected with adjacent whitespaces that must be handled"
  (is (= 
      [[ 0  1  2  3  4]
       [ 5  6  7  8  9]
       [10 11 12 13 14]
       [15 16 17 18 19]
       [20 21 22 23 24]]
      (read-single-board "0  1  2  3  4\n5  6  7  8  9\n10 11 12 13 14\n 15 16 17 18 19\n 20 21 22 23 24")))))

(deftest test-string-split-and-parse
  (testing "splitting a string without whitespaces"
  (is (= [1 2 3] (string-split-and-parse "1 2 3" #" "))))
  
  (testing "splitting a string that has whitespaces which need trimming"
  (is (= [1 2 3] (string-split-and-parse " 1 2 3 " #" "))))
  
  (testing "splitting a string that has multiple whitespaces in-between values"
  (is (= [1 2 3] (string-split-and-parse "1  2     3" #" +")))))