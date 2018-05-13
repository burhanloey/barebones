(ns barebones.admin.calendar.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::week-data
 (fn [db _]
   (:week-data (:panel-content db))))

(rf/reg-sub
 ::last-7-dates
 (fn [db _]
   (:last-7-dates (:panel-content db))))

(rf/reg-sub
 ::heatmap-data
 (fn [db _]
   (:heatmap-data (:panel-content db))))

(rf/reg-sub
 ::days-legend
 (fn [db _]
   (:days-legend (:panel-content db))))

(rf/reg-sub
 ::months-legend
 (fn [db _]
   (:months-legend (:panel-content db))))
