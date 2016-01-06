(defproject http-kit-view-demo "0.1.0-SNAPSHOT"

  :description "Views Demo using http-kit/websockets"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [
                 [org.clojure/clojure "1.7.0"]

                 ;; Web
                 [http-kit "2.1.18"]
                 [ring "1.4.0"]
                 [ring.middleware.logger "0.5.0"]
                 [compojure "1.4.0"]

                 ;; Client
                 [org.clojure/clojurescript "1.7.170"]
                 [reagent "0.6.0-alpha"]
                 [views "1.4.4" :exclusions [prismatic/plumbing]] ; assumes environ dependency!?

                 ;; Various Util
                 [org.clojure/core.async "0.2.374"]
                 [com.cognitect/transit-cljs "0.8.237"]
                 [com.cognitect/transit-clj "0.8.285"]
                 [environ "1.0.1"]
                 [prismatic/schema "1.0.4"]
                 [prismatic/plumbing "0.5.2"] ; ensure most current version for om-tools?

                 [views/honeysql "0.1.2"]
                 [com.h2database/h2 "1.4.190"]
                 ]
               
  :main http-kit-view-demo.core               

  :plugins [[lein-cljsbuild "1.1.2"]
            [environ "1.0.1"]]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src/http_kit_view_demo/client"]
              :compiler
              {:main          http-kit-view-demo.client.core
               :source-map    "resources/static/js/cljs/main.js.map"
               :output-to     "resources/static/js/cljs/main.js"
               :output-dir    "resources/static/js/cljs"
               :asset-path    "js/cljs"
               :optimizations :none
               :pretty-print  true}}]})
