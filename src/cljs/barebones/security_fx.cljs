(ns barebones.security-fx
  (:require [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [re-frame.core :as rf]))

(rf/reg-event-fx
 ::get-csrf-token
 (fn-traced
  [_ [_ handlers]]  ; :on-success and :on-failure handlers
  {:http-xhrio (merge
                {:method :get
                 :uri "/token"
                 :timeout 10000
                 :response-format (ajax/json-response-format {:keywords? true})}
                handlers)}))

(rf/reg-event-fx
 ::handle-csrf-token
 (fn-traced
  [_ [_ req {:keys [token]}]]
  {:http-xhrio (assoc req :headers {:x-xsrf-token token})}))

;; An effect to request anti CSRF token which will then be used as X-XSRF-Token
;; header before making POST request. Effect argument is 100% the same as
;; :http-xhrio effect.
;;
;; Example usage:
;;
;; (ns barebones.login.events
;;   (:require ...
;;             [barebones.security-fx]  <-- Add this
;;             ...))
;;
;; ...
;;
;; (rf/reg-event-fx
;;  ::login
;;  (fn-traced
;;   [{:keys [login-input]} _]
;;   {:http-xhrio-xsrf {:method :post  <-- Use :http-xhrio-xsrf instead of :http-xhrio
;;                      :uri "/admin/login"
;;                      :timeout 10000
;;                      :response-format (ajax/json-response-format {:keywords? true})
;;                      :on-success [::success-handler]
;;                      :on-failure [::failure-handler]
;;                      :body login-input}}))
;;
;; ...
(rf/reg-fx
 :http-xhrio-xsrf
 (fn [{:keys [on-failure] :as req}]
   (rf/dispatch [::get-csrf-token {:on-success [::handle-csrf-token req]
                                   :on-failure on-failure}])))
