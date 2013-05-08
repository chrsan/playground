(ns clojure-snippets.fsm1-logic
  (:refer-clojure :exclude [==])
  (:use clojure.core.logic))

(declare state1 state2)

(defn- state0 [str out]
  (conde
   [(emptyo str) (== :accept out)]
   [(fresh [a d]
      (conso a d str)
      (conde
       [(== 0 a) (state0 d out)]
       [(== 1 a) (state1 d out)]))]))

(defn- state1 [str out]
  (conde
   [(== str ()) (== :reject out)]
   [(fresh [a d]
      (conso a d str)
      (conde
       [(== 0 a) (state2 d out)]
       [(== 1 a) (state0 d out)]))]))

(defn- state2 [str out]
  (conde
   [(== str ()) (== :reject out)]
   [(fresh [a d]
      (conso a d str)
      (conde
       [(== 0 a) (state1 d out)]
       [(== 1 a) (state2 d out)]))]))

(defn- fsm-hoo [str out]
  (state0 str out))

(defn- first-ten []
  (run 10 [q]
    (fresh [str out]
      (fsm-hoo str out)
      (conso str out q))))

(defn -main [& args]
  (println "011 =>" (first (run* [q] (fsm-hoo [0 1 1] q))))
  (println "0111 =>" (first (run* [q] (fsm-hoo [0 1 1 1] q))))
  (println "10 :accept =>" (run 10 [q] (fsm-hoo q :accept)))
  (println "10 :reject =>" (run 10 [q] (fsm-hoo q :reject)))
  (println "first 10 =>" (first-ten))
  (println (run* [q] (fsm-hoo [0 q] :accept))))
