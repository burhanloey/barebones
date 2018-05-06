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
    [:li
     [:a {:href href :class @active?}
      [:span.icon.is-medium icon] text]]))

(defn admin-page []
  (let [panel (rf/subscribe [::admin-subs/panel])]
    [:div#admin-page

     ;; Navbar
     [:nav.navbar.is-dark {:role "navigation" :aria-label "main navigation"}
      [:div.navbar-brand
       [:a.navbar-item {:href "#"} "barebones"]

       [:a.navbar-burger {:aria-label "menu" :aria-expanded false}
        [:span {:aria-hidden true}]
        [:span {:aria-hidden true}]
        [:span {:aria-hidden true}]]]

      [:div.navbar-menu
       [:div.navbar-end
        [:div.navbar-item.has-dropdown.is-hoverable
         [:a.navbar-link
          [:span.icon [:i.fas.fa-user]]]
         [:div.navbar-dropdown.is-right.is-boxed.has-background-grey-dark
          [:a.navbar-item
           {:on-click #(rf/dispatch [::admin-events/logout])}
           "Logout"]]]]]]

     [:div.columns
      [:div.column.is-2
       ;; Nav left sidebar
       [:aside.menu
        [:ul.menu-list
         [nav-link {:href "#" :icon [:i.fas.fa-tachometer-alt]} "Dashboard"]
         [nav-link {:href "#/location" :icon [:i.fas.fa-map]} "Location"]
         [nav-link {:href "#/calendar" :icon [:i.far.fa-calendar-alt]} "Calendar"]
         [nav-link {:href "#/settings" :icon [:i.fas.fa-cog]} "Settings"]]]]

      ;; Content
      [:div#content-wrapper.column.has-background-grey
       (case @panel
         "Dashboard" [dashboard-panel]
         "Location" [location-panel]
         "Calendar" [calendar-panel]
         "Settings" [settings-panel]
         [:div])]]]))
