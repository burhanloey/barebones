(ns barebones.admin.dashboard.views)

(defn status-card [{:keys [icon background]} count text]
  [:div.col-md-3.mb-3
   [:div.card.text-white {:class (str "bg-" background)}
    [:div.card-header
     [:div.row
      [:div.col icon]
      [:div.col.text-right [:h1 count] text]]]

    [:a.view-details {:href "#"}
     [:div.card-footer.d-flex.bg-light {:class (str "text-" background)}
      [:span "View Details"]
      [:span.ml-auto [:i.fas.fa-arrow-circle-right]]]]]])

(defn dashboard-panel []
  [:div.mt-4
   [:h3.border-bottom.pb-2.mb-4 "Issues"]

   [:div.row
    [status-card
     {:icon [:i.fas.fa-check.fa-5x]
      :background "success"}
     50 "Solved"]

    [status-card
     {:icon [:i.fas.fa-spinner.fa-5x]
      :background "primary"}
     25 "In Progress"]

    [status-card
     {:icon [:i.fas.fa-list-ul.fa-5x]
      :background "danger"}
     5 "To Do"]]])
