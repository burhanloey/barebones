(ns barebones.admin.location.views
  (:require [reagent.core :as r]))

(defn world-map []
  (r/create-class
   {:component-did-mount
    (fn [this]
      (let [l (-> js/L
                  (.map (r/dom-node this))
                  (.setView #js [4.848 100.737] 13))]
        (-> js/L
            (.tileLayer "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        #js {:attribution "&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors"})
            (.addTo l))

        (-> js/L
            (.marker #js [4.848 100.737])
            (.bindPopup "Issue #54 reported here")
            (.addTo l))))

    :render
    (fn []
      [:div {:style {:height "480px"}}])}))

(defn location-panel []
  [:div
   [:section.section
    [:div.box
     [:h3.title.is-6.is-uppercase "Location"]
     [world-map]]]])
