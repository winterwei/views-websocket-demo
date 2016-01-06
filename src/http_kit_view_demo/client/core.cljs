(ns http-kit-view-demo.client.core
  (:require-macros
   [cljs.core.async.macros :refer [go]])
  (:require
   [cljs.core.async :refer [chan sub <! map< put!]]
   [reagent.core :as r]
   [http-kit-view-demo.client-lib.websockets :refer [ws-chans send-transit-msg! receive-transit-msg-fn! views-ws-url make-websocket!]]))

(def app-data (r/atom {}))

(defn msg-input
  []
  (let [msg (r/atom "")]
    (fn []
      [:div.input {:style #js {:marginTop "12px"}}
       [:input#msg {:type      "text"
                    :value     @msg
                    :on-change #(reset! msg (.. % -target -value))
                    :on-key-up #(when (= 13 (.-keyCode %))
                                 (send-transit-msg! (get @ws-chans "views") @msg)
                                 (reset! msg ""))}]
       [:button {:on-click #(send-transit-msg! (get @ws-chans "views") @msg)}
        "Send message"]])))

(defn app
  []
  [:div.main
   [:div.messages {:style {:width "50%" :display "table-cell" :valign "top"}}
    [:h2 "Chat!!"]

    [(msg-input)]

    [:div.comments
     (for [comment (:comments @app-data)]
       (into [:div.comment {:style #js {:borderBottom "1px solid black" :marginRight "3px" :padding "5px"}}]
             comment))]]])

(defn update-view!
  [msg]
  (.log js/console "GOT MSG: " (pr-str msg))
  (swap! app-data assoc (ffirst msg) (second msg)))

(defn init!
  []
  (let [rfn (receive-transit-msg-fn! update-view!)
        ws  (make-websocket! views-ws-url "views" rfn)]
    (r/render-component [app] (.getElementById js/document "app-root"))))

(set! (.-onload js/window) init!)
