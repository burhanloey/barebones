(ns barebones.login.events
  (:require [ajax.core :as ajax]
            [barebones.events :as events]
            [barebones.login.db]
            [barebones.security-fx]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [re-frame.core :as rf]))

(rf/reg-event-fx
 ::login
 [(rf/inject-cofx :login-input)]
 (fn-traced
  [{:keys [login-input]} _]
  {:http-xhrio-xsrf {:method :post
                     :uri "/admin/login"
                     :timeout 10000
                     :params login-input
                     :format (ajax/json-request-format)
                     :response-format (ajax/json-response-format {:keywords? true})
                     :on-success [::events/set-page :admin]
                     :on-failure [::events/set-page :login]}})) ; TODO: show error
