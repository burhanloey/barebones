(ns barebones.views
  (:require [re-frame.core :as rf]))

(defn nav-link [{:keys [href icon]} text]
  [:a.nav-link.border-bottom {:href href}
   [:div.row
    [:span.col-3.text-center icon]
    text]])

(defn main-panel []
  [:div

   ;; Navbar
   [:nav.navbar.navbar-expand-lg.navbar-light.bg-light.border-bottom
    [:a.navbar-brand {:href "#"} "barebones"]
    [:button.btn.btn-outline-success.ml-auto "Logout"]]

   [:div.row
    ;; Nav left sidebar
    [:nav.nav.flex-column.col-md-2.bg-light
     [nav-link {:href "#" :icon [:i.fas.fa-tachometer-alt]} "Dashboard"]
     [nav-link {:href "#" :icon [:i.fas.fa-map]} "Location"]
     [nav-link {:href "#" :icon [:i.far.fa-calendar-alt]} "Calendar"]]

    ;; Content
    [:div#content-wrapper.col.border-left
     [:div.container
      [:h1.border-bottom "Dashboard"]]]]])
