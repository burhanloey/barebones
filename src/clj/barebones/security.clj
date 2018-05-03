(ns barebones.security
  (:require [buddy.auth :refer [authenticated?]]
            [buddy.auth.accessrules :refer [wrap-access-rules]]
            [buddy.auth.backends :as backends]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.sign.jwt :as jwt]
            [clojure.java.io :as io]
            [cprop.core :refer [load-config]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.util.response :refer :all]))

(def conf (load-config))

(def secret (:secret conf))
(def backend (backends/jws {:secret secret}))

(defn csrf-handler [req]
  (-> (response {:status 403 :message "Invalid anti-forgery token"})
      (status 403)))

(defn unauthorized-handler [req val]
  (-> (response {:status 403 :message "Not authorized"})
      (status 403)))

(defn sign [claims]
  (jwt/sign claims secret))


;; Access rules

(defn admin-access [req]
  (authenticated? req))

(def access-rules [{:pattern #"^/admin/api/.*"
                    :handler admin-access}])


;; Middleware

(defn wrap-security
  [handler]
  (-> handler
      (wrap-access-rules {:rules access-rules :on-error unauthorized-handler})
      (wrap-authentication backend)
      (wrap-authorization backend)
      (wrap-anti-forgery {:error-handler csrf-handler})))
