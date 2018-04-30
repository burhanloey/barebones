(ns barebones.view
  (:require [hiccup.page :refer [html5 include-js include-css]]))

(defn admin-page [req]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title "Admin Page"]
    (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css")]
   [:body
    [:h1 "Admin Dashboard"]
    [:div#app]
    (include-js "js/compiled/app.js")
    [:script "barebones.core.init();"]]))
