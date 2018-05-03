(ns barebones.db
  (:require [re-frame.core :as rf]))

(def default-db {:page :loading})

;; (rf/reg-cofx
;;  ::token-in-cookie
;;  (fn [cofx _]
;;    (assoc cofx :token (second (re-find #"token=([^;]+)" (.-cookie js/document))))))
