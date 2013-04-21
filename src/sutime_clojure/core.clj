; SUTime is part of standard pipeline in StanfordCoreNLP
; This is a wrapper around just the SUTime portion
; We grab the Timex annotations and return just the unique timex
;; annotations.

(ns sutime-clojure.core
  (:gen-class :main true)
  (:require [clojure.string :as string]
    		    [clojure.xml :as xml]
            [clj-time.core :as clj-time]
            [clj-time.format :as clj-time-format]
            [clojure.zip :as zip])
  (:import [edu.stanford.nlp.pipeline StanfordCoreNLP Annotation]
           [edu.stanford.nlp.ling CoreAnnotations 
                                  CoreAnnotations$TokensAnnotation 
                                  CoreAnnotations$NamedEntityTagAnnotation
                                  CoreAnnotations$DocDateAnnotation]
           [edu.stanford.nlp.time TimeAnnotations$TimexAnnotation]
           [java.util Properties Date]))

(def ^:dynamic *properties* (new Properties))
(. *properties* put "annotators" "tokenize, ssplit, pos, lemma, ner, parse, dcoref")

(def *stanford-core-nlp-obj* (new StanfordCoreNLP *properties*))

(def custom-formatter (clj-time-format/formatter "yyyy-MM-dd"))

(defn tokenize
  "Passes the given string through the stanford NER tagger"
  ([s] (tokenize s (clj-time-format/unparse custom-formatter (clj-time/now))))
  ([s reference-date]
   (let [document (new Annotation s)]
      (do
        (. document set CoreAnnotations$DocDateAnnotation (.toString reference-date))
      	(. *stanford-core-nlp-obj* annotate document)
      	(. document get CoreAnnotations$TokensAnnotation)))))

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

(defn fetch-date-strings
  "
  	Input: String s
  	We return a set of strings of the type YYYY-MM-DD i.e just the date
  "
  ([s] (fetch-date-strings s (clj-time-format/unparse custom-formatter (clj-time/now))))
  ([s reference-time]
   (map
    #(first (string/split % #"T"))
  	(map first 
        (partition-by identity 
                      (map
    				          #(-> % :attrs :value)
    				          (filter identity 
                		          (flatten 
                                (map #(zip-str (. % toString)) 
                                  (fetch-timex-annotations (tokenize s reference-time)))))))))))
