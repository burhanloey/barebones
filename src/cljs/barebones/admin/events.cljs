(ns barebones.admin.events
  (:require [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [re-frame.core :as rf]))

(rf/reg-event-db
 ::set-panel
 (fn-traced
  [db [_ panel]]
  (assoc db :panel panel)))
