(ns sutime-clojure.core-test
  (:use clojure.test
        sutime-clojure.core))

(deftest raw-dates-test
  (is (= ["2012-04-10" "2012-04-11" "2013-01-10"]
         (sutime-clojure.core/fetch-date-strings 
           "Apr 10 2012 and Apr 11 2012 was when I discovered that I was a mushroom.
            I discovered I was a toadstool on 2013 January 10"))))

(deftest reference-date-test
  (is (= ["2013-04-10"] (sutime-clojure.core/fetch-date-strings "April 10"))))