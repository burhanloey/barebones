(ns barebones.loading.views)

(defn loading-page []
  [:div {:style {:width "100%"
                 :height "100%"
                 :top 0
                 :left 0
                 :position "fixed"
                 :display "block"
                 :background-color "#e0e0e0"
                 :z-index 99
                 :text-align "center"
                 :font-family "serif"
                 :font-size "200%"
                 :color "white"}}
   [:span {:style {:position "absolute"
                   :top "45%"
                   :left "50%"
                   :transform "translate(-45%, -50%)"}}
    "Loading..."]])
