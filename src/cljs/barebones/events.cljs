(ns barebones.events
  (:require [barebones.db :as db]
            [re-frame.core :as rf]))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
