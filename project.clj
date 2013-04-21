(defproject sutime-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [edu.stanford.nlp/stanford-corenlp "1.3.4"]
                 [edu.stanford.nlp/stanford-corenlp "1.3.4" :classifier "models"]
                 [clj-time "0.5.0"]]
  :jvm-opts ["-Xmx2000M"]
  :main sutime-clojure.core)
