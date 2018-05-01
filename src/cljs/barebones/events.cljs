(ns barebones.events
  (:require [barebones.db :as db]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [re-frame.core :as rf]))

(rf/reg-event-fx
 ::initialize-db
 [(rf/inject-cofx ::db/token-in-cookie)]
 (fn [{:keys [token]} _]
   {:db (assoc db/default-db :token token)}))

(rf/reg-event-db
 ::set-panel
 (fn [db [_ panel]]
   (assoc db :panel panel)))

(rf/reg-event-db
 ::set-page
 (fn [db [_ page]]
   (assoc db :page page)))

(rf/reg-event-fx
 ::verify-token
 (fn [{:keys [db]} _]
   {:http-xhrio {:uri "/admin/api/v1/me"
                 :method :get
                 :timeout 10000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :headers {"Authorization" (str "Token " (:token db))}
                 :on-success [::set-page :admin]
                 :on-failure [::set-page :login]}}))
