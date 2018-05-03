(ns barebones.login.events
  (:require [ajax.core :as ajax]
            [barebones.login.db]
            [barebones.security-fx]
            [day8.re-frame.http-fx]
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
                     :response-format (ajax/json-response-format {:keywords? true})
                     :on-success [::login-success]
                     :on-failure [::login-success]
                     :body login-input}}))

(rf/reg-event-db
 ::login-success
 (fn-traced
  [db _]
  (js/console.log "Ehh")
  db))
