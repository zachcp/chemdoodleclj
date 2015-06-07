(defproject chemdoodleclj "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2816"]
                 [reagent "0.5.0"]
                 [cljsjs/react "0.12.2-5"]
                 [cljsjs/kemia "0.2.0"]
                 [cljsjs/chemdoodle "7.0.1"]
                 ]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-cljsbuild "1.0.6"]]
  :source-paths ["src"]
  :clean-targets ["out"]
  :target-path "target"
  :cljsbuild {
     :builds [{
         :source-paths ["src"]
         :compiler {
           :output-to "out/main.js"
           :optimizations :whitespace
           :pretty-print true}}]})
