(ns hospital.aula1
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(defn adiciona-paciente
  [pacientes, paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

; { 15 [], 20 [], 25 [] }
(defn adiciona-visita
  [visitas, paciente, novas-visitas]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(defn imprime-relatorio-de-paciente [visitas, paciente]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-de-pacientes []
  (let [guilherme {:id 15, :nome "Guilherme"}
        daniela {:id 20, :nome "Daniela"}
        paulo {:id 25, :nome "Paulo"}

        ; uma variação com reduce, mais natural
        pacientes (reduce adiciona-paciente {} [guilherme, daniela, paulo])

        ; uma variação com shadowing, fica feio
        visitas {}
        visitas (adiciona-visita visitas 15 ["01/01/2019"])
        visitas (adiciona-visita visitas 20 ["01/02/2019", "01/01/2020"])
        visitas (adiciona-visita visitas 15 ["01/03/2019"])]
    (pprint pacientes)
    (pprint visitas)

    ; cacas grandes na vida pois estou usando o símbolo paciente
    ; em vários lugares do meu programa com significados diferentes
    (imprime-relatorio-de-paciente visitas daniela)
    (println (get visitas 20))))

(testa-uso-de-pacientes)

(pprint (s/validate Long 15))
;(pprint (s/validate Long "guilherme"))
;(pprint (s/validate Long [15, 13]))

(s/set-fn-validation! true)


(s/defn teste-simples
  [x :- Long]
  (println x))

(teste-simples 30)
;(teste-simples "guilherme")

(s/defn imprime-relatorio-de-paciente
  [visitas, paciente :- Long]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

; agora conseguimos o erro em tempo de *execução* que diz
; que o valor passado como parametro nao condiz com o schema Long
;(testa-uso-de-pacientes)


(s/defn novo-paciente
  [id :- Long, nome :- s/Str]
  { :id id, :nome nome})

(pprint (novo-paciente 15 "Guilherme"))
;(pprint (novo-paciente "Guilherme" 15))
