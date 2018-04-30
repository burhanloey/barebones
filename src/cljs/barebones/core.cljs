(ns barebones.core
  (:require [barebones.config :as config]
            [barebones.events :as events]
            [barebones.routes :as routes]
            [barebones.views :as views]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (r/render [views/main-panel] (js/document.getElementById "app")))

(defn ^:export init []
  (routes/app-routes)
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
