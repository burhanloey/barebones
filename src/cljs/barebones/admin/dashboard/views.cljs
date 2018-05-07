(ns barebones.admin.dashboard.views)

(defn status-card [{:keys [icon]} count text]
  [:div.column.is-3
   [:div.box.has-text-white-ter.has-background-grey-dark
    [:div.columns
     [:div.column icon]
     [:div.column.has-text-right
      [:span.title.is-1.has-text-white count] [:br]
      [:span text]]]]])

(defn dashboard-panel []
  [:div
   [:section.section
    [:h3.title.is-6.has-text-white-ter.is-uppercase "Issues"]

    [:div.columns
     [status-card
      {:icon [:i.fas.fa-check.fa-5x]}
      50 "Solved"]

     [status-card
      {:icon [:i.fas.fa-spinner.fa-5x]}
      25 "In Progress"]

     [status-card
      {:icon [:i.fas.fa-list-ul.fa-5x]}
      5 "To Do"]]]])
