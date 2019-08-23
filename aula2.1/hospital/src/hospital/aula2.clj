(ns hospital.aula2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

;(s/defrecord Paciente
;  [id :- Long, nome :- s/Str])
;
;(pprint (map->Paciente {15 "Guilherme"}))
;(pprint (map->Paciente {"15" "Guilherme"}))

(def Paciente
  "Schema de um paciente"
  {:id s/Num, :nome s/Str})

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 15, :nome "guilherme"}))

; typo é pego pelo schema. mas poderiamos argumentar que esse
; tipo de erro seria pego em testes automatizados com cobertura boa
;(pprint (s/validate Paciente {:id 15, :name "guilherme"}))


; mas.... entra a questão de QUERER ser forward compatible OU NÃO
; entender esse trade-off
; sistemas externos não me quebrarão ao adicionar campos novos (foward compatible)
; no nosso validate não estamos sendo forward compatible (pode ser interessante quando quero analisar mudanças)
;(pprint (s/validate Paciente {:id 15, :nome "guilherme", :plano [:raio-x]}))

; chaves que são keywords em schemas são por padrão OBRIGATÓRIAS
;(pprint (s/validate Paciente {:id 15}))

; tipo de retorno com schema
; força a validação na saída da função
;(s/defn novo-paciente :- Paciente
;  [id :- s/Num, nome :- s/Str]
;  { :id id, :nome nome, :plano [] })

(s/defn novo-paciente :- Paciente
  [id :- s/Num, nome :- s/Str]
  { :id id, :nome nome })

(pprint (novo-paciente 15 "Guilherme"))




