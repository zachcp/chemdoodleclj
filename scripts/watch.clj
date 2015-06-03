(require '[cljs.closure :as cljsc])

(cljsc/watch "src"
  {:main 'chemdoodleclj.core
   :output-to "out/chemdoodleclj.js"
   :output-dir "out"
   :optimizations :none
   :cache-analysis true
   :source-map true})
