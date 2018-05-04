(ns barebones.admin.events
  (:require [ajax.core :as ajax]
            [barebones.events :as events]
            [barebones.security-fx]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [re-frame.core :as rf]))

(rf/reg-event-db
 ::set-panel
 (fn-traced
  [db [_ panel]]
  (assoc db :panel panel)))

(rf/reg-event-fx
 ::logout
 (fn-traced
  [_ _]
  {:http-xhrio-xsrf {:method :post
                     :uri "/admin/logout"
                     :timeout 10000
                     :format (ajax/json-request-format)
                     :response-format (ajax/json-response-format {:keywords? true})
                     :on-success [::events/set-page :login]
                     :on-failure [::events/set-page :admin]}}))
