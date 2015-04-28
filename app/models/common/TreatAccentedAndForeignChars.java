package models.common;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public class TreatAccentedAndForeignChars {
    
    public static String treat(String s) {

        for (int i = 0; i < foreignChars.length; i++) {
            String pattern = foreignChars[i];

            if(s.matches(pattern))
               s = s.replace(pattern, foreignCharsSubstitutes[i]);
        }

        return s;
    }
    
    static String[] foreignChars = new String[] {
        "/ä|æ|ǽ/",
        "/ö|œ/",
        "/ü/",
        "/Ä/",
        "/Ü/",
        "/Ö/",
        "/À|Á|Â|Ã|Ä|Å|Ǻ|Ā|Ă|Ą|Ǎ/",
        "/à|á|â|ã|å|ǻ|ā|ă|ą|ǎ|ª/",
        "/Ç|Ć|Ĉ|Ċ|Č/",
        "/ç|ć|ĉ|ċ|č/",
        "/Ð|Ď|Đ/",
        "/ð|ď|đ/",
        "/È|É|Ê|Ë|Ē|Ĕ|Ė|Ę|Ě/",
        "/è|é|ê|ë|ē|ĕ|ė|ę|ě/",
        "/Ĝ|Ğ|Ġ|Ģ/",
        "/ĝ|ğ|ġ|ģ/",
        "/Ĥ|Ħ/",
        "/ĥ|ħ/",
        "/Ì|Í|Î|Ï|Ĩ|Ī|Ĭ|Ǐ|Į|İ/",
        "/ì|í|î|ï|ĩ|ī|ĭ|ǐ|į|ı/",
        "/Ĵ/",
        "/ĵ/",
        "/Ķ/","/ķ/",
        "/Ĺ|Ļ|Ľ|Ŀ|Ł/",
        "/ĺ|ļ|ľ|ŀ|ł/",
        "/Ñ|Ń|Ņ|Ň/",
        "/ñ|ń|ņ|ň|ŉ/",
        "/Ò|Ó|Ô|Õ|Ō|Ŏ|Ǒ|Ő|Ơ|Ø|Ǿ/",
        "/ò|ó|ô|õ|ō|ŏ|ǒ|ő|ơ|ø|ǿ|º/",
        "/Ŕ|Ŗ|Ř/",
        "/ŕ|ŗ|ř/",
        "/Ś|Ŝ|Ş|Š/",
        "/ś|ŝ|ş|š|ſ/",
        "/Ţ|Ť|Ŧ/",
        "/ţ|ť|ŧ/",
        "/Ù|Ú|Û|Ũ|Ū|Ŭ|Ů|Ű|Ų|Ư|Ǔ|Ǖ|Ǘ|Ǚ|Ǜ/",
        "/ù|ú|û|ũ|ū|ŭ|ů|ű|ų|ư|ǔ|ǖ|ǘ|ǚ|ǜ/",
        "/Ý|Ÿ|Ŷ/",
        "/ý|ÿ|ŷ/",
        "/Ŵ/","/ŵ/",
        "/Ź|Ż|Ž/",
        "/ź|ż|ž/",
        "/Æ|Ǽ/",
        "/ß/",
        "/Ĳ/",
        "/ĳ/",
        "/Œ/",
        "/ƒ/"
    };

    static String[] foreignCharsSubstitutes = new String[] {
        "ae",
        "oe",
        "ue",
        "Ae",
        "Ue",
        "Oe",
        "A",
        "a",
        "C",
        "c",
        "D",
        "d",
        "E",
        "e",
        "G",
        "g",
        "H",
        "h",
        "I",
        "i",
        "J",
        "j",
        "K",
        "k",
        "L",
        "l",
        "N",
        "n",
        "O",
        "o",
        "R",
        "r",
        "S",
        "s",
        "T",
        "t",
        "U",
        "u",
        "Y",
        "y",
        "W",
        "w",
        "Z",
        "z",
        "AE",
        "ss",
        "IJ",
        "ij",
        "OE",
        "f"
    };
}
