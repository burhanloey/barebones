(ns barebones.admin.calendar.events
  (:require [barebones.admin.calendar.db :refer [default-db]]
            [barebones.admin.calendar.utils :as utils]
            [cljs.core.async :refer [<! >! chan]]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [re-frame.core :as rf])
  (:require-macros [cljs.core.async :refer [go]]))

;; Events

(rf/reg-event-fx
 ::init-calendar
 (fn-traced
  [{:keys [db]} _]
  {:db (if (nil? (:panel-content db))
         (assoc db :panel-content default-db)
         db)

   ;; TODO: load from server
   :calendar {"2017-05-28" 5
              "2017-06-14" 10
              "2017-06-15" 25
              "2017-05-11" 3
              "2018-05-08" 1
              "2018-05-09" 4}}))

(rf/reg-event-fx
 ::update-week-chart
 (fn-traced
  [_ [_ chart data labels]]
  {:week-chart {:chart chart
                :data data
                :labels labels}}))

(rf/reg-event-db
 ::update-heatmap-data
 (fn-traced
  [db [_ heatmap-data]]
  (assoc-in db [:panel-content :heatmap-data] heatmap-data)))

(rf/reg-event-db
 ::update-months-legend
 (fn-traced
  [db [_ months-legend]]
  (assoc-in db [:panel-content :months-legend] months-legend)))

(rf/reg-event-db
 ::update-week-data
 (fn-traced
  [db [_ week-data]]
  (assoc-in db [:panel-content :week-data] week-data)))

(rf/reg-event-db
 ::update-last-7-dates
 (fn-traced
  [db [_ last-7-dates]]
  (assoc-in db [:panel-content :last-7-dates] last-7-dates)))


;; Effects

(rf/reg-fx
 :week-chart
 (fn [{:keys [chart data labels]}]
   (set! (-> chart .-data .-datasets first .-data) (clj->js data))
   (set! (-> chart .-data .-labels) (clj->js labels))
   (.update chart)))

(rf/reg-fx
 :calendar
 (fn [year-data]
   (let [details (chan)
         last-7-days (chan)
         last-7-dates (chan)
         week-data (chan)
         heatmap-data (chan)
         days-legend (chan)
         months-legend (chan)]
     (go (let [d (<! details)]
           (>! last-7-days (take-last 7 d))
           (>! heatmap-data (utils/generate-calendar-heatmap-data d))))
     (go (let [hd (<! heatmap-data)]
           (>! months-legend (utils/generate-months-legend-data hd))
           (rf/dispatch [::update-heatmap-data hd])))
     (go (let [l7d (<! last-7-days)]
           (>! last-7-dates (map :date-obj l7d))
           (>! week-data (map :count l7d))))

     (go (rf/dispatch [::update-months-legend (<! months-legend)]))
     (go (rf/dispatch [::update-week-data (<! week-data)]))
     (go (rf/dispatch [::update-last-7-dates (<! last-7-dates)]))

     ;; Initiate
     (go (>! details (utils/find-details-for-year-data year-data))))))
