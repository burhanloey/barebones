(ns barebones.db
  (:require [re-frame.core :as rf]))

(def default-db {:page :loading})

(rf/reg-cofx
 ::token-in-cookie
 (fn [cofx _]
   (assoc cofx :token "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjo1MjkxMDN9.FEU-QRtzSQi8FHyO_ywmdEYgYF_cpc_RQ8H7BshtzZE")))
