(ns rest-clojure.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]))


(defn hello-handler [req]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (json/write-str {:msg "hello"})})

(defn todo-handler [req]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (json/write-str {:msg "hello"})})

(defn todo-find-handler [req]
  (let [id (-> req :params :id)]
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body (json/write-str {:msg "hello"
                            :data id})}))

(defn todo-insert-handler [req]
  (let [body (json/read-str (slurp (-> req :body)))]
    (clojure.pprint/pprint body))
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (json/write-str {:msg "hello"})})

(defn todo-update-handler [req]
  (let [body (json/read-str (slurp (-> req :body)))]
    (clojure.pprint/pprint body))
  (let [id (-> req :params :id)]
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body (json/write-str {:msg "hello"
                            :data id})}))

(defn todo-delete-handler [req]
  (let [id (-> req :params :id)]
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body (json/write-str {:msg "hello"
                            :data id})}))

(defroutes app-routes
  (GET "/" [] hello-handler)
  (GET "/todo" [] todo-handler)
  (GET "/todo/:id" [id] todo-find-handler)
  (POST "/todo" [] todo-insert-handler)
  (PUT "/todo/:id" [id] todo-update-handler)
  (DELETE "/todo/:id" [id] todo-delete-handler))

(defn -main [& args]
  (let [port 3000]
    (server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))