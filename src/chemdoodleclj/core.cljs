(ns chemdoodleclj.core
  (:require [reagent.core :as reagent :refer [atom]]
            [cljsjs.chemdoodle]
            [kemia.model.Atom]
            [kemia.model.Bond]
            [kemia.model.Molecule]
            [kemia.io.smiles.SmilesParser]
            [kemia.layout.CoordinateGenerator]
            ))
            
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
    (clj->js
      {"a" (map process_atom atoms)
       "b" (map #(process_bond % Mol) bonds)})))

(defn chemdoodle 
  "put your chemdoodle JSON into Chemdoodle"
  [id json]
  (let [viewer (new ChemDoodle.ViewerCanvas id 100 100)
        jsoninterp (new ChemDoodle.io.JSONInterpreter)
        chemmol (jsoninterp/molFrom json)]
    (do
      (println "json" json)
      (println "chemmol" chemmol))
      (set! (.. viewer -specs -bonds_width_2D) 0.6 )
      (set! (.. viewer -specs -bonds_width_2D) 0.6 )
      (.scaleToAverageBondLength chemmol 15)
      ;viewACS.specs.bonds_width_2D = .6;
       ; viewACS.specs.bonds_saturationWidth_2D = .18;
      ;  viewACS.specs.bonds_hashSpacing_2D = 2.5;
       ; viewACS.specs.atoms_font_size_2D = 10;
      ;  viewACS.specs.atoms_font_families_2D = ['Helvetica', 'Arial', 'sans-serif'];
      ;  viewACS.specs.atoms_displayTerminalCarbonLabels_2D = true;
       ; var caffeineMolFile = 'Molecule Name\n  CHEMDOOD08070920033D 0   0.00000     0.00000     0\n[Insert Comment Here]\n 14 15  0  0  0  0  0  0  0  0  1 V2000\n   -0.3318    2.0000    0.0000   O 0  0  0  1  0  0  0  0  0  0  0  0\n   -0.3318    1.0000    0.0000   C 0  0  0  1  0  0  0  0  0  0  0  0\n   -1.1980    0.5000    0.0000   N 0  0  0  1  0  0  0  0  0  0  0  0\n    0.5342    0.5000    0.0000   C 0  0  0  1  0  0  0  0  0  0  0  0\n   -1.1980   -0.5000    0.0000   C 0  0  0  1  0  0  0  0  0  0  0  0\n   -2.0640    1.0000    0.0000   C 0  0  0  4  0  0  0  0  0  0  0  0\n    1.4804    0.8047    0.0000   N 0  0  0  1  0  0  0  0  0  0  0  0\n    0.5342   -0.5000    0.0000   C 0  0  0  1  0  0  0  0  0  0  0  0\n   -2.0640   -1.0000    0.0000   O 0  0  0  1  0  0  0  0  0  0  0  0\n   -0.3318   -1.0000    0.0000   N 0  0  0  1  0  0  0  0  0  0  0  0\n    2.0640   -0.0000    0.0000   C 0  0  0  2  0  0  0  0  0  0  0  0\n    1.7910    1.7553    0.0000   C 0  0  0  4  0  0  0  0  0  0  0  0\n    1.4804   -0.8047    0.0000   N 0  0  0  1  0  0  0  0  0  0  0  0\n   -0.3318   -2.0000    0.0000   C 0  0  0  4  0  0  0  0  0  0  0  0\n  1  2  2  0  0  0  0\n  3  2  1  0  0  0  0\n  4  2  1  0  0  0  0\n  3  5  1  0  0  0  0\n  3  6  1  0  0  0  0\n  7  4  1  0  0  0  0\n  4  8  2  0  0  0  0\n  9  5  2  0  0  0  0\n 10  5  1  0  0  0  0\n 10  8  1  0  0  0  0\n  7 11  1  0  0  0  0\n  7 12  1  0  0  0  0\n 13  8  1  0  0  0  0\n 13 11  2  0  0  0  0\n 10 14  1  0  0  0  0\nM  END\n> <DATE>\n07-08-2009\n';
      ;  var caffeine = ChemDoodle.readMOL(caffeineMolFile);
       ; caffeine.scaleToAverageBondLength(14.4);
    (.loadMolecule viewer chemmol)))

  (let [mol (parsesmiles "CC1CCCC1CCN")
       json (process_molecule mol)]
   (do
     (println json)
     (chemdoodle "chemdoodle" json)))


(comment

(let [mol (parsesmiles "CCCN")
      atoms (.-atoms mol)]
    (do
      (println "Get your Mol here " mol)
      (println "GEt your Atoms here " atoms)
      
      (println "Atom Count" (count (get mol "atoms")))
      (println "Name: " (.-name mol))
      (println "Bond Lenght: " (. mol (getAverageBondLength)))
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
             )
