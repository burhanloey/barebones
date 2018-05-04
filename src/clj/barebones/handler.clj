(ns barebones.handler
  (:require [barebones.auth :as auth]
            [barebones.security :as security :refer [wrap-security]]
            [barebones.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.util.response :refer :all]))

;; Handlers

(defn token [req]
  (response {:token *anti-forgery-token*}))

(defn me [req]
  (let [user (get-in req [:identity :user])]
    (response {:message (str "Hi, " user "!")})))


;; Routes

(defroutes admin-api-v1-routes
  (GET "/me" [] me))

(defroutes all-routes
  (GET "/admin" [] views/admin-page)
  (GET "/token" [] token)
  (POST "/admin/login" [] auth/login)
  (POST "/admin/logout" [] auth/logout)
  (context "/admin/api/v1" [] admin-api-v1-routes)
  (route/not-found (-> (response {:status 404 :message "Not found"})
                       (status 404))))

(def app
  (-> all-routes
      wrap-security
      wrap-keyword-params
      wrap-json-params
      wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
