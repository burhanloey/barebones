(ns barebones.login.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::show-error
 (fn [db _]
   (:show-error (:page-content db))))
