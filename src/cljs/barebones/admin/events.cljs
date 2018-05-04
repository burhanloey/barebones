(ns barebones.admin.events
  (:require [ajax.core :as ajax]
            [barebones.events :as events]
            [barebones.security-fx]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [re-frame.core :as rf]))

;; Events

(rf/reg-event-fx
 ::set-panel
 (fn-traced
  [{:keys [db]} [_ panel]]
  {:db (assoc db :panel panel)
   :panel-switch true}))

(rf/reg-event-db
 ::reset-panel-content
 (fn-traced
  [db _]
  (dissoc db :panel-content)))

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


;; Effects

(rf/reg-fx
 :panel-switch
 (fn [_]
   (rf/dispatch [::reset-panel-content])))
