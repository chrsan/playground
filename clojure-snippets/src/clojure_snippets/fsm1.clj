(ns clojure-snippets.fsm1)

(declare state1 state2)

(defn- state0 [str]
  (if (empty? str)
    :accept
    (let [d (rest str)]
      (case (first str)
        \0 (state0 d)
        \1 (state1 d)))))

(defn- state1 [str]
  (if (empty? str)
    :reject
    (let [d (rest str)]
      (case (first str)
        \0 (state2 d)
        \1 (state0 d)))))

(defn- state2 [str]
  (if (empty? str)
    :reject
    (let [d (rest str)]
      (case (first str)
        \0 (state1 d)
        \1 (state2 d)))))

(defn fsm-ho [str]
  (assert (every? #{\0 \1} str))
  (state0 str))

(defn -main [& args]
  (println "011 =>" (fsm-ho "011"))
  (println "0111 =>" (fsm-ho "0111")))
