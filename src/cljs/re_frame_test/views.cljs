(ns re-frame-test.views
  (:require
    [re-frame.core :as re-frame]
    [re-com.core :as re-com]
    [re-frame-test.subs :as subs]))

(re-frame/reg-sub
  :test-sub
  (fn [db]
    (:test-sub db)))

(re-frame/reg-sub
  :test-result
  (fn [db]
    (:test-result db)))

(re-frame/reg-event-db
  :test-event
  (fn [db [_ value]]
    (if (= 1 (first (shuffle [1 2])))
      (assoc db :test-sub value
                :test-result "Change accepted")
      (assoc db :test-result "Change ignored"))))

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label (str "Hello from " @name)
     :level :level1]))

(defn main-panel []
  (let [test   (re-frame/subscribe [:test-sub])
        result (re-frame/subscribe [:test-result])]
    [re-com/v-box
     :height "100%"
     :children [[title]
                [:span @test]
                [:span @result]
                [re-com/input-text
                 :model test
                 :on-change #(re-frame/dispatch [:test-event %])]]]))
