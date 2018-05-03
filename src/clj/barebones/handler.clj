(ns barebones.handler
  (:require [barebones.security :as security :refer [wrap-security]]
            [barebones.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer :all]))

;; Handlers

(defn token [req]
  (response {:token *anti-forgery-token*}))

(defn login [req]
  (response {:token (security/sign {:user 529103})}))

(defn test-login [req]
  (response {:message "Got ya"}))

(defn list-admin [req]
  (response {:admins [12345 23456 50284]}))

(defn me [req]
  (let [user (get-in req [:identity :user])]
    (response {:message (str "Hi, " user "!")})))


;; Routes

(defroutes admin-api-v1-routes
  (GET "/list" [] list-admin)
  (GET "/me" [] me))

(defroutes all-routes
  (GET "/admin" [] views/admin-page)
  (GET "/req" [] #(response %))
  (GET "/token" [] token)
  (GET "/admin/login" [] login)
  (POST "/admin/login" [] test-login)
  (context "/admin/api/v1" [] admin-api-v1-routes)
  (route/not-found (-> (response {:status 404 :message "Not found"})
                       (status 404))))

(def app
  (-> all-routes
      wrap-security
      wrap-json-body
      wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
