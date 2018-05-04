(ns barebones.admin.settings.views)

(defn settings-panel []
  [:div.col-md-6.mt-4
   [:h3.border-bottom.pb-2.mb-4 "Change password"]

   [:div.form-group
    [:label {:for "oldPassword"} "Old password"]
    [:input#oldPassword.form-control {:type "password"}]]

   [:div.form-group
    [:label {:for "newPassword"} "New password"]
    [:input#newPassword.form-control {:type "password"}]]

   [:div.form-group
    [:label {:for "confirmPassword"} "Confirm new password"]
    [:input#confirmPassword.form-control {:type "password"}]]

   [:button.btn
    "Update password"]

   [:h3.border-bottom.pb-2.mt-5.mb-4.text-danger "Delete account"]

   [:button.btn.btn-danger
    "Delete my account"]])
