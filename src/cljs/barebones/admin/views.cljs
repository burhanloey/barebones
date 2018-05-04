(ns barebones.admin.views
  (:require [barebones.admin.calendar.views :refer [calendar-panel]]
            [barebones.admin.dashboard.views :refer [dashboard-panel]]
            [barebones.admin.events :as admin-events]
            [barebones.admin.location.views :refer [location-panel]]
            [barebones.admin.settings.views :refer [settings-panel]]
            [barebones.admin.subs :as admin-subs]
            [re-frame.core :as rf]))

(defn nav-link [{:keys [href icon]} text]
  (let [active? (rf/subscribe [::admin-subs/active-navlink? text])]
    [:a.nav-link.border-bottom {:href href :class @active?}
     [:div.row
      [:span.col-3.text-center icon]
      text]]))

(defn admin-page []
  (let [panel (rf/subscribe [::admin-subs/panel])]
    [:div

     ;; Navbar
     [:nav.navbar.navbar-expand-lg.navbar-light.bg-light.border-bottom
      [:a.navbar-brand {:href "#"} "barebones"]
      [:button.btn.btn-outline-success.ml-auto
       {:on-click #(rf/dispatch [::admin-events/logout])}
       "Logout"]]

     [:div.row
      ;; Nav left sidebar
      [:nav.nav.flex-column.col-md-2.bg-light
       [nav-link {:href "#" :icon [:i.fas.fa-tachometer-alt]} "Dashboard"]
       [nav-link {:href "#/location" :icon [:i.fas.fa-map]} "Location"]
       [nav-link {:href "#/calendar" :icon [:i.far.fa-calendar-alt]} "Calendar"]
       [nav-link {:href "#/settings" :icon [:i.fas.fa-cog]} "Settings"]]

      ;; Content
      [:div#content-wrapper.col.border-left
       [:div.container.mt-4
        (case @panel
          "Dashboard" [dashboard-panel]
          "Location" [location-panel]
          "Calendar" [calendar-panel]
          "Settings" [settings-panel]
          [:div])]]]]))
