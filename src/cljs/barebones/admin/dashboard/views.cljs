(ns barebones.admin.dashboard.views)

(defn status-card [{:keys [icon background]} count text]
  [:div.column.is-3
   [:div.box {:class (str "has-background-" background)}
    [:div.columns.has-text-white
     [:div.column icon]
     [:div.column.has-text-right
      [:span.title.is-1.has-text-white count] [:br]
      [:span text]]]]])

(defn dashboard-panel []
  [:div
   [:section.section
    [:div.box
     [:h3.title.is-6.is-uppercase "Issues"]

     [:div.columns
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
       5 "To Do"]

      [status-card
       {:icon [:i.fas.fa-trash.fa-5x]
        :background "grey-light"}
       10 "Discarded"]]]]])
