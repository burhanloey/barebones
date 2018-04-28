(defproject barebones "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[buddy "2.0.0"]
                 [compojure "1.6.1"]
                 [cprop "0.1.11"]
                 [org.clojure/clojure "1.9.0"]
                 [org.eclipse.jetty/jetty-server "9.4.9.v20180320"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [ring/ring-json "0.4.0"]]
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler barebones.handler/app}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.2"]]}})
