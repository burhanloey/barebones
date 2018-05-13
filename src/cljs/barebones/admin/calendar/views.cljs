(ns barebones.admin.calendar.views
  (:require [barebones.admin.calendar.events :as calendar-events]
            [barebones.admin.calendar.subs :as calendar-subs]
            [reagent.core :as r]
            [re-frame.core :as rf]))

;; Week chart

(defn line-chart [data labels]
  (let [chart (atom nil)]
    (r/create-class
     {:component-did-mount
      (fn [this]
        (let [ctx (.getContext (r/dom-node this) "2d")]
          (reset! chart (js/Chart.
                         ctx
                         (clj->js
                          {:type "line"
                           :data {:labels labels
                                  :datasets [{:label "Issue count"
                                              :backgroundColor "rgb(47, 129, 123)"
                                              :data data}]}
                           :options {:title {:text "Issue count by week"}
                                     :elements {:line {:tension 0}}
                                     :legend {:labels {:fontColor "white"}}
                                     :scales
                                     {:xAxes [{:type "time"
                                               :time {:unit "day"}
                                               :ticks {:fontColor "white"}}]
                                      :yAxes [{:ticks {:fontColor "white"
                                                       :beginAtZero true
                                                       :suggestedMax 5
                                                       :stepSize 1}}]}}})))))

      :component-will-update
      (fn [_ [fn data labels]]
        (rf/dispatch [::calendar-events/update-week-chart @chart data labels]))

      :render
      (fn []
        [:canvas])})))

(defn week-chart []
  (let [week-data (rf/subscribe [::calendar-subs/week-data])
        labels (rf/subscribe [::calendar-subs/last-7-dates])]
    [:div.box.has-background-grey-dark
     [:h3.title.is-6.has-text-white-ter.is-uppercase "By week"]
     [line-chart @week-data @labels]]))


;; Year chart

(defn days-legend []
  (let [days-legend (rf/subscribe [::calendar-subs/days-legend])]
    [:g {:transform "translate(0, 13)"}
     (for [{:keys [key idx day y]} @days-legend]
       ^{:key key}
       [:text
        {:text-anchor "start" :x 0 :y y :fill "#f0ffff"}
        day])]))

(defn months-legend []
  (let [months-legend (rf/subscribe [::calendar-subs/months-legend])]
    [:g {:transform "translate(30, 8)"}
     (for [{:keys [key idx month x]} @months-legend]
       ^{:key key}
       [:text
        {:text-anchor "start" :x x :y 0 :fill "#f0ffff"}
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
  [:div.box.has-background-grey-dark
   [:h3.title.is-6.has-text-white-ter.is-uppercase "By year"]
   [:svg.has-background-grey
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
