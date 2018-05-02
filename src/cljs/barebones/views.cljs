(ns barebones.views
  (:require [barebones.admin.views :refer [admin-page]]
            [barebones.loading.views :refer [loading-page]]
            [barebones.login.views :refer [login-page]]
            [barebones.subs :as subs]
            [re-frame.core :as rf]))

(defn app-page []
  (let [page (rf/subscribe [::subs/page])]
    (case @page
      :admin [admin-page]
      :loading [loading-page]
      :login [login-page]
      [:div])))
