(ns barebones.admin.calendar.views
  (:require [barebones.admin.calendar.events :as calendar-events]
            [barebones.admin.calendar.subs :as calendar-subs]
            [reagent.core :as r]
            [re-frame.core :as rf]))

;; Week chart

(defn line-chart [data labels]
  (r/create-class
   {:component-did-mount
    (fn [this]
      (rf/dispatch [::calendar-events/init-week-chart this data labels]))

    :component-will-update
    (fn [_ [_ data labels]]
      (rf/dispatch [::calendar-events/update-week-chart data labels]))

    :display-name "week-chart"

    :reagent-render
    (fn [data labels]
      [:canvas])}))

(defn week-chart []
  (let [data (rf/subscribe [::calendar-subs/week-data])
        labels (rf/subscribe [::calendar-subs/last-7-dates])]
    [:div.box
     [:h3.title.is-6.is-uppercase "By week"]
     [line-chart @data @labels]]))


;; Year chart

(defn days-legend []
  (let [days-legend (rf/subscribe [::calendar-subs/days-legend])]
    [:g {:transform "translate(0, 13)"}
     (for [{:keys [key idx shown day y]} @days-legend]
       (when shown
         ^{:key key}
         [:text
          {:text-anchor "start" :x 0 :y y}
          day]))]))

(defn months-legend []
  (let [months-legend (rf/subscribe [::calendar-subs/months-legend])]
    [:g {:transform "translate(30, 8)"}
     (for [{:keys [key idx month x]} @months-legend]
       ^{:key key}
       [:text
        {:text-anchor "start" :x x :y 0}
        month])]))

(defn calendar-heatmap []
  (let [heatmap-data (rf/subscribe [::calendar-subs/heatmap-data])]
    [:g
     (for [{:keys [key transform week details x]} @heatmap-data]
       ^{:key key}
       [:g {:transform transform}
        (for [{:keys [key day date count shade size y title]} details]
          (when date
            ^{:key key}
            [:rect {:width size :height size
                    :x 0 :y y
                    :fill shade}
             [:title title]]))])]))

(defn year-chart []
  [:div.box
   [:h3.title.is-6.is-uppercase "By year"]
   [:svg
    {:width "100%" :height "100%" :viewBox "0 0 1048 150"}
    [:g {:transform "translate(10, 10)"}
     [calendar-heatmap]
     [days-legend]
     [months-legend]]]])

(defn calendar-panel []
  [:div
   [:section.section
    [:div.columns
     [:div.column.is-6
      [week-chart]]]
    [:div.columns
     [:div.column
      [year-chart]]]]])
