(ns barebones.subs
  (:require [clojure.string :as str]
            [re-frame.core :as rf]))

(rf/reg-sub
 ::page
 (fn [db _]
   (:page db)))

(rf/reg-sub
 ::panel
 (fn [db _]
   (:panel db)))

(rf/reg-sub
 ::active-navlink?
 :<- [::panel]
 (fn [panel [_ text]]
   (if (= text panel)
     "active"
     "")))
