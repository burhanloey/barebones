(ns barebones.admin.subs
  (:require [re-frame.core :as rf]))

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
