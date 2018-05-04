(ns barebones.events
  (:require [ajax.core :as ajax]
            [barebones.db :as db]
            [day8.re-frame.http-fx]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [re-frame.core :as rf]))

;; Events

(rf/reg-event-db
 ::initialize-db
 (fn-traced
  [_ _]
  db/default-db))

(rf/reg-event-fx
 ::set-page
 (fn-traced
  [{:keys [db]} [_ page]]
  {:db (assoc db :page page)
   :page-switch true}))

(rf/reg-event-db
 ::reset-page-content
 (fn-traced
  [db _]
  (dissoc db :page-content)))

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


;; Effects

(rf/reg-fx
 :page-switch
 (fn [_]
   (rf/dispatch [::reset-page-content])))
