(ns barebones.admin.calendar.db
  (:require [barebones.admin.calendar.utils :as utils]))

;; (def default-heatmap-data
;;   (utils/generate-calendar-heatmap-data
;;    (utils/find-details-for-year-data {})))

;; (def default-months-legend
;;   (utils/generate-months-legend-data default-heatmap-data))

(def default-db {;; :heatmap-data default-heatmap-data
                 ;; :months-legend default-months-legend
                 :days-legend (utils/generate-days-legend)
                 :week-data (repeat 7 0)
                 :last-7-dates (map #(-> (js/moment)
                                         (.startOf "day")
                                         (.subtract % "day"))
                                    (reverse (range 7)))})
