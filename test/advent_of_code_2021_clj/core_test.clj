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

(deftest test-read-numbers
  (testing "reading numbers"
  (is (= [7 4 9 5 11 17 23 2 0 14 21 24 10 16 13 6 15 25 12 22 18 20 8 19 3 26 1]
         (read-numbers "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1")))))

(deftest test-parse-input
  (testing "parsing the input file"
  (is (= {:numbers [31 88 35 24]
          :boards [[[50 83  3 31 16]
                    [47  9 94 10 86]
                    [61 22 53 46 74]
                    [77 41 79 55 62]
                    [97 78 43 73 40]]
                   [[99 96 20 35 21]
                    [38 17 48 69 68]
                    [ 9 51 32 52 11]
                    [67  8 42 89 27]
                    [39 62 66 72 43]]]}
         (parse-input "31,88,35,24

50 83  3 31 16
47  9 94 10 86
61 22 53 46 74
77 41 79 55 62
97 78 43 73 40

99 96 20 35 21
38 17 48 69 68
 9 51 32 52 11
67  8 42 89 27
39 62 66 72 43")))))