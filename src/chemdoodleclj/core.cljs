(ns chemdoodleclj.core
  (:require [clojure.browser.repl :as repl]
            [kemia.model.Atom]
            [kemia.model.Bond]
            [kemia.model.Molecule]
            [kemia.io.smiles.SmilesParser]
            [kemia.layout.CoordinateGenerator]))

; (defonce conn
;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(println "Hello world!")

(defn parsesmiles 
  "use kemia to parse smiles"
  [smiles]
  (let [mol (kemia.io.smiles.SmilesParser.parse smiles)
        mol2 (kemia.layout.CoordinateGenerator.generate mol)]
    mol2))
    
(defn process_atom [^kemia.model.Atom Atom]
  "take a kemia Atom and return json ready for chemdoodle"
  (let [coord (.-coord Atom)] 
   {"l" (.-symbol Atom)
    "x" (.-x coord)
    "y" (.-y coord)
    "c" (.-charge Atom)}))

(defn process_bond [^kemia.model.Bond Bond ^kemia.model.Molecule Mol]
  "take a kemia Bond and return json ready for chemdoodle"
  (let [atom1 (.-source Bond)
        atom2 (.-target Bond)]
   {"b" (.indexOfAtom Mol atom1)
    "e" (.indexOfAtom Mol atom2)
    "o" (.-order Bond)
    }))

(defn process_molecule [^kemia.model.Molecule Mol]
  "kemia Molecule to chemdoodle JSON"
  (let [atoms (.-atoms Mol)
        bonds (.-bonds Mol)]
    {"a" (map process_atom atoms)
     "b" (map #(process_bond % Mol) bonds)}))

(defn chemdoodle 
  "put your chemdoodle JSON into Chemdoodle"
  [id json]
  (let [viewer (new ChemDoodle.ViewerCanvas id 100 100)
        chemmol (new ChemDoodle.io.JSONInterpreter (.molFrom json))]
    (.loadMolecule viewercanvas chemmol)))

(let [mol (parsesmiles "CCCN")
      json (process_molecule)]
  (chemdoodle "chemdoodle" json))
  
(let [mol (parsesmiles "CCCN")
      atoms (.-atoms mol)]
    (do
      (println "Get your Mol here " mol)
      (println "GEt your Atoms here " atoms)
      
      (println "Atom Count" (count (get mol "atoms")))
      (println "Name: " (.-name mol))
      (println "Bond Lenght: " (. mol (getAverageBondLength)))
;      (println (.. atoms mol))
;      (println (.atoms mol))
      (println (.-atoms mol))
      (println (.-bonds mol))
      (println (.-atoms mol))))

      (let [smiles "CNCNCNCN"    
           mol  (js/kemia.io.smiles.SmilesParser.parse smiles)
           mol2D (kemia.layout.CoordinateGenerator/generate mol)
           atoms (.-atoms mol2D)
           atoms (.-bonds mol2D)
           processed (process_molecule mol2D)      
           ]
        (do
         (println (process_molecule mol2D))
         (println (clj->js processed))
         (println mol2D)
         (println (map #(.-coord %) (.-atoms mol2D)))
         (println smiles)

         (println mol2D)
         (println "test")
         ;(println (.-atoms mol2D ))
         ;(println (.-bonds mol2D ))
         (println "test")
         (for [atm (.-atoms mol2D)]
           (do
             (println atm)
             (println (.-symbol atm))
             (println (.-coord atm))
             (println (.-isotope atm))
             (println (.-aromatic atm))))))

      (println "Hello Again!")
  
  
