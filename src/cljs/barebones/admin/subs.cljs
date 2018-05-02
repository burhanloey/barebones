(ns barebones.admin.subs
  (:require [barebones.subs :as subs]
            [re-frame.core :as rf]))

(rf/reg-sub
 ::active-navlink?
 :<- [::subs/panel]
 (fn [panel [_ text]]
   (if (= text panel)
     "active"
     "")))
