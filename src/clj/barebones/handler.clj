(ns barebones.handler
  (:require [barebones.security :as security :refer [wrap-security]]
            [barebones.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.util.response :refer :all]))

(def users [{:username "ahmad" :password "abc123"}
            {:username "ali" :password "asdfjkl"}])

(defn lookup-user [username]
  (first (filter #(= (:username %) username) users)))

;; Handlers

(defn token [req]
  (response {:token *anti-forgery-token*}))

(defn login [req]
  (let [username (get-in req [:params :username])
        password (get-in req [:params :password])
        remember (get-in req [:params :remember])
        user (lookup-user username)]
    (if (= (:password user) password)
      ;; Put identity in session
      (let [session (:session req)
            identity (select-keys user [:username])
            updated-session (assoc session :identity identity)
            authenticated-resp (-> (response {:message "OK"})
                                   (assoc :session updated-session))]
        (if remember
          (assoc authenticated-resp :session-cookie-attrs {:max-age 31557600})
          authenticated-resp))
      ;; else, give 401 error
      (-> (response {:status 401
                     :message "Wrong username/password"})
          (status 401)))))

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
  (GET "/req" [] #(str %))
  (GET "/token" [] token)
  (POST "/admin/login" [] login)
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
