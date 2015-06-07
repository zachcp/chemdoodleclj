(defproject chemdoodleclj "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3123"]
                 [reagent "0.5.0"]
                 [cljsjs/kemia "0.2.0"]
                 [cljsjs/chemdoodle "7.0.1"]
                 ]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :node-dependencies [[source-map-support "0.2.8"]]
  :plugins [[lein-npm "0.4.0"]
            [lein-cljsbuild "1.0.6"]]
  :source-paths ["src"]
  :clean-targets ["out" "release"]
  :target-path "target"
  :cljsbuild {
     :builds [{
         :source-paths ["src"]
         :compiler {
           :output-to "out/chemdoodleclj.js"
           :optimizations :none
           :pretty-print true}}]})
