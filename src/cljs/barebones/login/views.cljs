(ns barebones.login.views
  (:require [barebones.login.events :as login-events]
            [barebones.login.subs :as login-subs]
            [re-frame.core :as rf]))

(defn login-page []
  (let [show-error? (rf/subscribe [::login-subs/show-error])]
    [:div.container
     [:section.section
      [:div.columns
       [:div.column.is-4.is-offset-4
        [:div.box

         [:p.title.is-6.is-center "Sign in"]

         [:form
          [:div.field
           [:div.control
            [:input#username.input {:placeholder "Username" :type "text"}]]]

          [:div.field
           [:div.control
            [:input#password.input {:placeholder "Password" :type "password"}]]]

          [:div.field
           [:div.control
            [:label.checkbox
             [:input#remember {:type "checkbox"}]
             "Remember me"]]]

          [:div.field
           [:div.control
            [:button.button.is-primary.is-fullwidth
             {:on-click (fn [evt]
                          (.preventDefault evt)
                          (rf/dispatch [::login-events/login]))}
             "Login"]]]]]

        (when @show-error?
          [:div.notification.is-warning.has-text-centered
           "Wrong username or password." [:br] "Guess which one was wrong!"])]]]]))
