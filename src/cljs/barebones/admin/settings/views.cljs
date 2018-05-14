(ns barebones.admin.settings.views)

(defn change-password []
  [:section.section
   [:div.box
    [:h3.title.is-6.is-uppercase "Change password"]

    [:div.field
     [:label.label
      {:for "old-password"} "Old password"]
     [:div.control
      [:input#old-password.input {:type "password"}]]]

    [:div.field
     [:label.label
      {:for "new-password"} "New password"]
     [:div.control
      [:input#new-password.input {:type "password"}]]]

    [:div.field
     [:label.label
      {:for "confirm-password"} "Confirm new password"]
     [:div.control
      [:input#confirm-password.input {:type "password"}]]]

    [:div.field
     [:div.control
      [:button.button.is-primary
       "Update password"]]]]])

(defn delete-account []
  [:section.section
   [:div.box
    [:h3.title.is-6.is-uppercase "Delete account"]

    [:button.button.is-danger
     "Delete my account"]]])

(defn settings-panel []
  [:div.columns
   [:div.column.is-6
    [change-password]
    [delete-account]]])
