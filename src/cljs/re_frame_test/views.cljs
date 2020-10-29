(ns re-frame-test.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [re-frame-test.subs :as subs]))

(re-frame/reg-sub :test-sub
  (fn [db]
    (:test-sub db)))

(re-frame/reg-event-db :test-event
  (fn [db [_ value]]
    (assoc db :test-sub value)))

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label (str "Hello from " @name)
     :level :level1]))

(defn main-panel []
  (let [test (re-frame/subscribe [:test-sub])]
    [re-com/v-box
     :height "100%"
     :children [[title]
                [:span @test]
                [re-com/input-text
                 :model test
                 :on-change #(re-frame/dispatch [:test-event %])]]]))
