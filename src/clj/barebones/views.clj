(ns barebones.views
  (:require [hiccup.page :refer [html5]]))

(defn include-js
  "Returns Hiccup view to include javascript file."
  [src & [integrity]]
  (let [attr {:src src}]
    [:script (if integrity
               (assoc attr :integrity integrity :crossorigin "anonymous")
               attr)]))

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
    (include-css "https://unpkg.com/leaflet@1.3.1/dist/leaflet.css"
                 "sha512-Rksm5RenBEKSKFjgI3a41vrjkw4EVPlJ3+OiI65vTjIdo9brlAacEuKOiQ5OFh7cOI1bkDwLqdLw3Zg0cRJAAQ==")
    (include-css "https://use.fontawesome.com/releases/v5.0.10/css/all.css"
                 "sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg")
    (include-css "/css/style.css")]
   [:body
    [:div#app]
    (include-js "https://unpkg.com/leaflet@1.3.1/dist/leaflet.js"
                "sha512-/Nsx9X4HebavoBvEBuyp3I7od5tA0UzAxs+j83KgC8PU0kgB4XiK4Lfe4y4cgBtaRJQEIFCW+oC506aPT2L1zw==")
    (include-js "https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.1/moment.min.js")
    (include-js "https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js")
    (include-js "js/compiled/app.js")
    [:script "barebones.core.init();"]]))
