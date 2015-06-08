(defproject chemdoodleclj "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3123"]
                 [prismatic/dommy "1.1.0"]
                 [cljsjs/kemia "0.2.0"]
                 [cljsjs/chemdoodle "7.0.1"]]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-cljsbuild "1.0.6"]]
  :source-paths ["src" "target/classes"]
  :clean-targets ["out" "release"]
  :target-path "target"
  :cljsbuild {
      :builds {:dev
                {:source-paths ["src"]
                  :compiler {:output-to "chemdoodleclj.js"
                           :optimizations :whitespace
                           :pretty-print true}}
               :prod
                {:source-paths ["src"]
                 :compiler {:output-to "chemdoodleclj.min.js"
                          :optimizations :advanced
                          :pretty-print true}}}})