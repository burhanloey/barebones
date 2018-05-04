(ns barebones.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [barebones.admin.events :as admin-events]
            [goog.events :as gevents]
            [goog.history.EventType :as EventType]
            [re-frame.core :as rf]
            [secretary.core :as secretary]))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
    (rf/dispatch [::admin-events/set-panel "Dashboard"]))

  (defroute "/location" []
    (rf/dispatch [::admin-events/set-panel "Location"]))

  (defroute "/calendar" []
    (rf/dispatch [::admin-events/set-panel "Calendar"]))

  (defroute "/settings" []
    (rf/dispatch [::admin-events/set-panel "Settings"]))

  (hook-browser-navigation!))
