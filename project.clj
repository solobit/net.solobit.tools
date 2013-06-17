(defproject net.solobit.tools "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://solobit.net"

  :min-lein-version  "2.0.0"

  :repositories {"project" "file:repo"}

  ;:source-paths      ["src/clojure"]
  ;:java-source-paths ["src/java"]
  ;:aot [org.nasa.worldwind.example]

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojure-contrib "1.2.0"]

                 [org.clojure/tools.namespace "0.2.3"]
                 [org.clojure/tools.trace "0.7.5"]

                 [swiss-arrows "0.6.0"]
                 [clojure-opennlp "0.3.0"]]

  :repl-options
  {:prompt (fn [ns] (str "Your mission for <" ns ">, sir? " ))
  ;; What to print when the repl session starts.
  :welcome (println "At ease soldier.")})
