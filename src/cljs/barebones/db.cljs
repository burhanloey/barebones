(ns barebones.db
  (:require [re-frame.core :as rf]))

;; App-state structure:

;; {:page :admin
;;  :token "sometoken"
;;  :panel "Dashboard"
;;  :content {:solved 34
;;            :in-progress 10
;;            :todo 50}}

(def default-db {:page :loading
                 :panel "Dashboard"})

(rf/reg-cofx
 ::token-in-cookie
 (fn [cofx _]
   (assoc cofx :token "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjo1MjkxMDN9.FEU-QRtzSQi8FHyO_ywmdEYgYF_cpc_RQ8H7BshtzZE")))
