(ns barebones.auth
  (:require [buddy.hashers :as hashers]
            [ring.util.response :refer :all]))

(def users [{:username "ahmad" :password "bcrypt+sha512$a6ce29b5466b5a620278a48ca78fa680$12$23e212765a6bf044b40cbc766720cb6ab359d8dd9a260ae2"}  ; pass: abc123
            {:username "ali" :password "bcrypt+sha512$afa60762b54d0bf7312431b90b1a2d39$12$5aed17738ae1d8ef16dbb86db4bdf387736686d91c2c9aca"}])  ; pass: asdfjkl

(defn lookup-user [username]
  (first (filter #(= (:username %) username) users)))


(defn login [req]
  (let [username (get-in req [:params :username])
        password (get-in req [:params :password])
        remember (get-in req [:params :remember])
        user (lookup-user username)]
    (if (hashers/check password (:password user))
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
      (-> (response {:status 401 :message "Wrong username/password"})
          (status 401)))))

(defn logout [req]
  (-> (response {})
      (assoc :session {})))
