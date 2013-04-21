(defproject sutime-clojure "0.1.0-SNAPSHOT"
  :description "Wrapper around the time functionality in Stanford NLP Suite"
  :url "https://github.com/shriphani/sutime-clojure"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [edu.stanford.nlp/stanford-corenlp "1.3.4"]
                 [edu.stanford.nlp/stanford-corenlp "1.3.4" :classifier "models"]
                 [clj-time "0.5.0"]]
  :jvm-opts ["-Xmx2000M"]
  :main sutime-clojure.core)
