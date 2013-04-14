(ns sutime-clojure.core-test
  (:use clojure.test
        sutime-clojure.core))

(deftest raw-dates-test
  (is (= ["2012-04-10" "2012-04-11" "2013-01-10"]
         (sutime-clojure.core/fetch-raw-date-strings 
           "Apr 10 2012 and Apr 11 2012 was when I discovered that I was a mushroom.
            I discovered I was a toadstool on 2013 January 10"))))

(deftest fixed-dates-test
  (is (= (sutime-clojure.core/fix-incomplete-date "XXXX-04-10" "2012-10-04")
      "2012-04-10")))