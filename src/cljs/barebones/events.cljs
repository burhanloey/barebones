(ns barebones.events
  (:require [ajax.core :as ajax]
            [barebones.db :as db]
            [barebones.admin.events :as admin-events]
            [day8.re-frame.http-fx]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [re-frame.core :as rf]))

;; Events

(rf/reg-event-db
 ::initialize-db
 (fn-traced
  [_ _]
  db/default-db))

(rf/reg-event-db
 ::set-page
 (fn-traced
  [db [_ page]]
  (assoc db :page page)))

(rf/reg-event-fx
 ::verify-identity
 (fn-traced
  [{:keys [db]} _]
  {:http-xhrio {:uri "/admin/api/v1/me"
                :method :get
                :timeout 10000
                :response-format (ajax/json-response-format {:keywords? true})
                :on-success [::set-page :admin]
                :on-failure [::set-page :login]}}))
