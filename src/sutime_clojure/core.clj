; SUTime is part of standard pipeline in StanfordCoreNLP
; This is a wrapper around just the SUTime portion
; We grab the Timex annotations and return just the unique timex
;; annotations.

(ns sutime-clojure.core
  (:gen-class :main true)
  (:require [clojure.string :as string]
    		[clojure.xml :as xml]
            [clojure.zip :as zip])
  (:import [edu.stanford.nlp.pipeline StanfordCoreNLP Annotation]
           [edu.stanford.nlp.ling CoreAnnotations CoreAnnotations$TokensAnnotation CoreAnnotations$NamedEntityTagAnnotation]
           [edu.stanford.nlp.time TimeAnnotations$TimexAnnotation]
           [java.util Properties]))

(def ^:dynamic *properties* (new Properties))
(. *properties* put "annotators" "tokenize, ssplit, pos, lemma, ner, parse, dcoref")

(def *stanford-core-nlp-obj* (new StanfordCoreNLP *properties*))

(defn tokenize
  "Passes the given string through the stanford NER tagger"
  [s]
  (let [document (new Annotation s)]
    (do
    	(. *stanford-core-nlp-obj* annotate document)
    	(. document get CoreAnnotations$TokensAnnotation))))

(defn fetch-timex-annotations
  "Filters out the timex annotations from a previously NER tagged string"
  [tokens]
  (map
  	#(. % get TimeAnnotations$TimexAnnotation)
	(filter 
		#(or (= (. % get CoreAnnotations$NamedEntityTagAnnotation) "DATE") 
	     	 (= (. % get CoreAnnotations$NamedEntityTagAnnotation) "TIME"))
		tokens)))

(defn zip-str
  "stolen from clojure xml docs"
  [s]
  (zip/xml-zip (xml/parse (java.io.ByteArrayInputStream. (.getBytes s)))))

(defn fetch-raw-date-strings
  "
  	Input: String s
  	We return a set of strings of the type YYYY-MM-DDTHH:MM
  "
  [s]
  (map
    #(first (string/split % #"T"))
  	(map first 
       (partition-by identity 
                (map
    				#(-> % :attrs :value)
    				(filter identity 
                		(flatten 
                           (map #(zip-str (. % toString)) 
                                (fetch-timex-annotations (tokenize s))))))))))

(defn fix-incomplete-date
  "Given a reference date we populate fields in the date-str
  that are not filled (i.e marked by XXXX or XX)"
  [date-str reference-date-str]
  (let [date-str-map (zipmap [:y :m :d] (string/split date-str #"-"))
        reference-date-map (zipmap [:y :m :d] (string/split reference-date-str #"-"))
        merge-date-maps (fn [d1 d2]
                          {:y (if (= (:y d1) "XXXX")
                                (:y d2)
                                (:y d1))
                           :m (if (= (:m d1) "XX")
                                (:m d2)
                                (:m d1))
                           :d (if (= (:d d1) "XX")
                                (:d d2)
                                (:d d1))})]
        (let [merged (merge-date-maps date-str-map reference-date-map)]
          (string/join "-" [(:y merged) (:m merged) (:d merged)]))))
