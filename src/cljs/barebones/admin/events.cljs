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

(rf/reg-event-fx
 ::test-ajax
 (fn-traced
  [_ _]
  {:http-xhrio {:uri "/admin/api/v1/me"
                :method :get
                :timeout 10000
                :response-format (ajax/json-response-format {:keywords? true})
                :on-success [::test-success]
                :on-failure [::test-failed]}}))

(rf/reg-event-db
 ::test-success
 (fn-traced
  [db _]
  (js/console.log "Success!")
  db))

(rf/reg-event-db
 ::test-failed
 (fn-traced
  [db _]
  (js/console.log "Failed!")
  db))
