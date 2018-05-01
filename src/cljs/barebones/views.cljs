(ns barebones.views
  (:require [barebones.subs :as subs]
            [re-frame.core :as rf]))

(defn nav-link [{:keys [href icon]} text]
  (let [active? (rf/subscribe [::subs/active-navlink? text])]
    [:a.nav-link.border-bottom {:href href :class @active?}
     [:div.row
      [:span.col-3.text-center icon]
      text]]))

(defn admin-page []
  [:div

   ;; Navbar
   [:nav.navbar.navbar-expand-lg.navbar-light.bg-light.border-bottom
    [:a.navbar-brand {:href "#"} "barebones"]
    [:button.btn.btn-outline-success.ml-auto "Logout"]]

   [:div.row
    ;; Nav left sidebar
    [:nav.nav.flex-column.col-md-2.bg-light
     [nav-link {:href "#" :icon [:i.fas.fa-tachometer-alt]} "Dashboard"]
     [nav-link {:href "#/location" :icon [:i.fas.fa-map]} "Location"]
     [nav-link {:href "#/calendar" :icon [:i.far.fa-calendar-alt]} "Calendar"]]

    ;; Content
    [:div#content-wrapper.col.border-left
     [:div.container
      [:h1.border-bottom "Dashboard"]]]]])

(defn loading-page []
  [:div
   [:p "Loading Page"]])

(defn login-page []
  [:div
   [:p "Login Page"]])

(defn app-page []
  (let [page (rf/subscribe [::subs/page])]
    (case @page
      :loading [loading-page]
      :login [login-page]
      :admin [admin-page]
      [:div])))
