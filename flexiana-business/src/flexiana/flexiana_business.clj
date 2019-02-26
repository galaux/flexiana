(ns flexiana.flexiana-business)


(defn char-count
  [s]
  (->> (group-by identity s)
       (map (fn [[char list]] [char (count list)]))
       (into {})))

(defn scramble?
  "Return true if a portion of str1 characters can be rearranged to match str2.
   This function intentionally does not change case nor filter-out characters,
   input checking is thus left to the user."
  [str1 str2]
  (let [chars1 (char-count str1)
        chars2 (char-count str2)]
    (every? (fn [[char count]]
              (>= (get chars1 char 0)
                  count))
            chars2)))
