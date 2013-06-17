(ns net.solobit.tools

  ;; The functions in these namespaces are so useful at the (Insta)REPL that I
  ;; want them 'use'd. I.e. I want to be able to type 'source' rather than use
  ;; 'clojure.contrib.repl-utils/source'. Although one can also nowadays use
  ;; (:require [a.b.c :refer [source]]) for sparse loading of functions.

  (:require [clojure.repl :refer [dir doc find-doc]]
            [swiss-arrows.core :refer :all]
            [clojure.string :as string]
            [clojure.pprint :refer [pprint print-table]]
            [clojure.reflect :refer [reflect]]
            [clojure.contrib.repl-utils :refer
             [apropos ;; regex-or-str) => currently-loaded ns that match
              get-source ;; 'quoted-form) => source-code of function
              javadoc
              expression-info ;; expr) => {:class <name> :primitive? <bool>}
              show ;; FIXME?
              ]])

  (:use [clojure.tools.namespace]
        ;;[clojure.contrib.repl-utils]
        [clojure.inspector]
        [clojure.tools.trace]))



(-<> (with-out-str (doc apropos))
     (string/replace <> #"\n" "")
     (string/replace <> #"(-){4,}" "㎱ 》")
     (string/split <> #"(/|\(|\))")
     (string/replace <> #"/" "\n")
     (string/trim <>))

(defn spaced-function-list
  "Returns the content of the namespace using (dir) but with
  println and newline intercepted and replaced with spaces for brevity."
  [ns]
  (replace (with-out-str (dir ns)) #"\n" " ")


(defn dump-compound-table
  "Shows all kinds of useful information on the argument provided."
  ([data]
  (print-table
   (sort-by :name
    (filter :exception-types (:members (reflect data))))))
  ;; same?
  ([data opt]
   (if (= opt :small)
     (print-table (:members (reflect data))))))



; Firstly we want to 'require' all the namespaces on the classpath
;; This ensures that find-doc and the like will work

(defn- require-may-fail [ns]
  "Some namespaces may fail to load, so catch any exceptions thrown.
  Private function for internal use only."
  (try
   (print "Attempting to require " ns ": ")
   (require ns)
   (println "success")
   (catch Exception e
     (println "couldn't require " ns "\nException\n" e "\n\n"))))

(defn require-all-namespaces-starting-with
  "Generally we'd want clojure.*, clojure.contrib.*, and any project-specific
  namespaces."
  [strng]
  (doall (map require-may-fail
              (filter #(. (str %) startsWith strng)
                      (find-namespaces-on-classpath)))))

(defn- stringify
  "Convience (doc re-pattern) instead of (find-doc \"re-pattern\").
  Can use macros so that (fd re-pattern) (fd \"re-pattern\") and
  (fd 're-pattern) all mean the same thing."
  [x]
  (println "stringify given" (str x))
  (let [s  (cond (string? x) x
                 (symbol? x) (str x)
                 (and (list? x) (= (first x) 'quote)) (str (second x))
                 :else (str x)) ]
    (println (str "translating to: \"" s "\""))
    s))

(defn- ns-publics-list
  "Sometimes I like to ask which public functions a namespace provides."
  [ns]
  (#(list (ns-name %)
          (map first (ns-publics %)))
    ns))

(defn- ns-refers-list
  "And occasionally which functions it pulls in (with refer or use)."
  [ns]
  (#(list (ns-name %) (map first
                           (ns-refers %)))
    ns))

(defmacro list-publics
  "Nice pretty-printed versions of these functions, accepting strings, symbols
  or quoted symbol."
  ([] `(pprint (ns-publics-list *ns*)))
  ([symbol-or-string] `(pprint (ns-publics-list
                                (find-ns
                                 (symbol (stringify '~symbol-or-string)))))))

(defmacro list-refers
  "Pretty-printed list of referer namespaces."
  ([] `(pprint (ns-refers-list *ns*)))
  ([symbol-or-string] `(pprint (ns-refers-list
                                (find-ns
                                 (symbol (stringify '~symbol-or-string)))))))



(defn list-all-ns
  "List all the namespaces."
  []
  (pprint (map ns-name (all-ns))))

(defn list-publics-all-ns
  "List all public functions in all namespaces!"
  []
  (pprint (map #(list (ns-name %) (map first
                                       (ns-publics %)))
               (all-ns))))

(list-publics-all-ns)

;; With all the namespaces loaded, find-doc can be overwhelming.
;; This is like find-doc, but just gives the associated names.

;; (defn- find-doc-names
;;   "Prints the name of any var whose documentation or name
;;   contains a match for re-string-or-pattern"
;;   [re-string-or-pattern]
;;     (let [re  (re-pattern re-string-or-pattern)]
;;       (doseq [ns (all-ns)
;;               v (sort-by (comp :name meta) (vals (ns-interns ns)))
;;               :when (and (:doc ^v)
;;                          (or
;;                           (re-find (re-matcher re (:doc ^v)))
;;                           (re-find (re-matcher re (str (:name ^v))))))]
;;                (print v "\n"))))


(defmacro fd
"Find symbol or string in docs."
  [symbol-or-string]
  `(find-doc (stringify '~symbol-or-string)))

(defmacro fdn
  "Find symbol or string in docs."
  [symbol-or-string]
  `(find-doc-names (stringify '~symbol-or-string)))


;;debugging macro                                try: (* 2 (dbg (* 3 4)))

(defmacro dbg
  "Debugging macro."
  [x]
  `(let [x# ~x]
     (do (println '~x "->" x#)
                  x#)))

(defmacro ppdbg
  "Pretty-printing version of debugger."
  [x]
  `(let [x# ~x]
     (do
       (println "--")(pprint '~x)
       (println "->")(pprint x#)
       (println "--")
       x#)))

(defmacro run-test
  "Debugger for running tests."
  [fn] `(test (resolve '~fn)))


(defn- get-classpath
  "Sometimes it's nice to check the classpath."
  []
   (sort (map (memfn getPath)
              (seq (.getURLs
                    (java.lang.ClassLoader/getSystemClassLoader))))))

(defn print-classpath []
  "Sometimes it's nice to check the classpath."
  (pprint (get-classpath)))

(defn get-current-directory []
  "Return the current directory."
  (. (java.io.File. ".") getCanonicalPath))





;; require everything from clojure and clojure.contrib
;; so that find-doc can find it
;(require-all-namespaces-starting-with "clojure")

;(println "Classpath:")
;(print-classpath)

;(println "Current Directory" (get-current-directory))

;;print the public functions in the current namespace
;(println "Current Namespace")
;(list-publics) ;=> current exposed to public

;(list-refers) ;=> big list

;;hint on how to require project specific namespaces
;(println "to require all namespaces starting with example:")
;(println "(require-all-namespaces-starting-with \"example\")")
;(require-all-namespaces-starting-with "clojure")
