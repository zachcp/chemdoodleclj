(ns chemdoodleclj.core
  (:require [cljsjs.chemdoodle]
            [kemia.model.Atom]
            [kemia.model.Bond]
            [kemia.model.Molecule]
            [kemia.io.smiles.SmilesParser]
            [kemia.layout.CoordinateGenerator]
            [dommy.core :as dommy :refer-macros [sel1]]))

(enable-console-print!)
(println "hello world")

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
    "o" (.-order Bond)}))

(defn process_molecule [^kemia.model.Molecule Mol]
  "kemia Molecule to chemdoodle JSON"
  (let [atoms (.-atoms Mol)
        bonds (.-bonds Mol)]
    (clj->js
      {"a" (map process_atom atoms)
       "b" (map #(process_bond % Mol) bonds)})))

(defn chemdoodle 
  "put your chemdoodle JSON into Chemdoodle"
  [id json]
  (let [viewer (new ChemDoodle.ViewerCanvas id 500 500)
       chemmol  (-> (new ChemDoodle.io.JSONInterpreter)
                    (.molFrom json))]
    (do
      (set! (.. viewer -specs -bonds_width_2D) 0.6 )
      (set! (.. viewer -specs -bonds_width_2D) 0.6 )
      (.scaleToAverageBondLength chemmol 15)
    (.loadMolecule viewer chemmol)))

(defn smiles-to-chemdoodle!
  []
  (let [smiles-input (sel1 :#smiles)
        smiles (dommy/value smiles-input)
        mol (parsesmiles smiles)
        json (process_molecule mol)]
    (println "smiles" smiles)
    (chemdoodle "chemdoodle" json)))

(dommy/listen! (sel1 :#submit) :click smiles-to-chemdoodle!))



(let [smiles "CCCC"
      mol (parsesmiles smiles)
      json (process_molecule mol)]
  (chemdoodle "chemdoodle" json))
