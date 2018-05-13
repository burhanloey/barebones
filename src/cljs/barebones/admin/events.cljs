(ns barebones.admin.events
  (:require [ajax.core :as ajax]
            [barebones.admin.calendar.events :as calendar-events]
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
   :panel-switch panel}))

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
 (fn [panel]
   (case panel
     "Calendar" (rf/dispatch [::calendar-events/init-calendar])
     nil)))
