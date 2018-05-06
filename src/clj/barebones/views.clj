(ns barebones.views
  (:require [hiccup.page :refer [html5]]))

(defn include-js
  "Returns Hiccup view to include javascript file."
  [src]
  [:script {:src src}])

(defn include-css
  "Returns Hiccup view to link stylesheet file, i.e, <link> tag. Takes optional
  integrity."
  [href & [integrity]]
  (let [attr {:rel "stylesheet"
              :href href}]
    [:link (if integrity
             (assoc attr :integrity integrity :crossorigin "anonymous")
             attr)]))


(defn admin-page [req]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title "Admin Page"]
    (include-css
     "https://use.fontawesome.com/releases/v5.0.10/css/all.css"
     "sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg")
    (include-css "/css/style.css")]
   [:body
    [:div#app]
    (include-js "js/compiled/app.js")
    [:script "barebones.core.init();"]]))
