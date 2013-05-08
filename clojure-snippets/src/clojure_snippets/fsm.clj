(ns clojure-snippets.fsm)

(def wiki-dfa
  {:q #{:S0 :S1 :S2}
   :sigma #{0 1}
   :delta {[:S0 0] :S0
           [:S0 1] :S1
           [:S1 0] :S2
           [:S1 1] :S0
           [:S2 0] :S1
           [:S2 1] :S2}
   :q0 :S0
   :f #{:S0}})

(defn fsm [machine str]
  (let [{q :q sigma :sigma delta :delta q0 :q0 f :f} machine]
    (loop [s str state q0]
      (cond
       (empty? s) (if (contains? f state) :accept :reject)
       :else (let [new-state (get delta [state (first s)])]
               (recur (rest s) new-state))))))

(defn -main [& args]
  (println (fsm wiki-dfa [0 1 1]))
  (println (fsm wiki-dfa [0 1 1 1]))
  (println (fsm wiki-dfa [1])))
