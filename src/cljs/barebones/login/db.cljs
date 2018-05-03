(ns barebones.login.db
  (:require [re-frame.core :as rf]))

(rf/reg-cofx
 :login-input
 (fn [cofx _]
   (let [username-input (js/document.getElementById "username")
         password-input (js/document.getElementById "password")
         remember-input (js/document.getElementById "remember")]
     (assoc cofx :login-input {:username (.-value username-input)
                               :password (.-value password-input)
                               :remember (.-checked remember-input)}))))
